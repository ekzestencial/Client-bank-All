package com.mycompany.client.bank.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.client.bank.api.LibAccount;
import com.mycompany.client.bank.api.LibAccountReply;
import com.mycompany.client.bank.api.LibBank;
import com.mycompany.client.bank.api.LibBankReply;
import com.mycompany.client.bank.api.LibTransaction;
import com.mycompany.client.bank.api.LibTransactionReply;
import com.mycompany.client.bank.api.PostRequest;
import com.mycompany.client.bank.auth.SecretProvider;
import com.mycompany.client.bank.api.TransferRequest;
import com.mycompany.client.bank.jpa.Account;
import com.mycompany.client.bank.jpa.Appuser;
import com.mycompany.client.bank.jpa.Bank;
import com.mycompany.client.bank.jpa.Transaction;
import com.mycompany.client.bank.services.AccountMapper;
import com.mycompany.client.bank.services.AccountService;
import com.mycompany.client.bank.services.AppUserAndUserDetailsService;
import com.mycompany.client.bank.services.BankMapper;
import com.mycompany.client.bank.services.BankService;
import com.mycompany.client.bank.services.TransactionMapper;
import com.mycompany.client.bank.services.TransactionService;
import com.mycompany.client.bank.utils.PermisChecker;

import static org.mockito.Matchers.anyDouble;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AccountController {
	
	@Autowired
	private AccountService accService;
	@Autowired
	private AccountMapper accMapper;
	@Autowired
	private TransactionService transService;
	@Autowired
	private TransactionMapper transMapper;
	@Autowired
	private BankService bankService;
	@Autowired
	private BankMapper bankMapper;
	@Autowired
	private AppUserAndUserDetailsService userService;
        @Autowired
        SecretProvider secretProvider;
	
	@RequestMapping(path = "/delete={accountId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public LibTransactionReply RemoveAllTransactions(@PathVariable String accountId) {
		LibTransactionReply reply = new LibTransactionReply();
		Long accountid = Long.valueOf(accountId);
		List<Transaction> tr = transService.getByAccountId(accountid);
		transService.deleteAllTransactions(tr);
		if (tr.isEmpty()) {
			reply.retcode = 1;

		} else {
			reply.retcode = 0;
		}
		return reply;
	}
	
	@RequestMapping(path="/account={accountId}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public LibTransactionReply getAccountInfoAndTransactions(@PathVariable String accountId) {
		
		if(!PermisChecker.ForUserAndAdmin(accService.getAccount(Long.valueOf(accountId)).getUserId().getUsername())) {
			return null;
		}
		
		LibTransactionReply reply = new LibTransactionReply();
		Long id = Long.valueOf(accountId);
		
		reply.account = accMapper.fromInternal(accService.getAccount(id));
		
		transService.getByAccountId(id).forEach(tr -> reply.transaction.add(transMapper.fromInternal(tr)));
		
		return reply;
	}
	
	@RequestMapping(path="/{username}/addNewAccount/{bank_name}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public LibAccountReply createAccount(@PathVariable String username, @PathVariable String bank_name) {
		LibAccountReply reply = new LibAccountReply();
		
		LibAccount libAcc = new LibAccount();
		libAcc.bankId = bankService.findByBankName(bank_name).getBankId().toString();
		libAcc.userId = userService.getUserByName(username).getUserId().toString();
		libAcc.value = "0";
		libAcc.credit_limit = "4000";
		
		Account acc = accMapper.toInternal(libAcc);
		accService.addAccount(acc);
		
		reply.accounts.add(accMapper.fromInternal(acc));
		return reply;
	}
	
	@RequestMapping(path="/banks", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public LibBankReply getBanks(@RequestBody LibBank req) {
                if(req==null)return new LibBankReply();
                Bank requeBank=bankMapper.toInternal(req);
                List<Bank> banks = new ArrayList<>();
                if(!requeBank.getName().isEmpty())banks.addAll(bankService.findByPartBankName(requeBank.getName()));
                else banks.addAll(bankService.getAll());
                banks.retainAll(bankService.findByBankPersent(requeBank.getDepositPersent(), requeBank.getCreditPersent()));
                LibBankReply reply=new LibBankReply();
                for(Bank bank : banks){
                reply.banks.add(bankMapper.fromInternal(bank));
                }
		return reply;
	}
	
	@RequestMapping(path="/{accountId}/transaction", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public LibTransactionReply changeWallet(@RequestBody TransferRequest req, @PathVariable String accountId) {
		LibTransactionReply reply = new LibTransactionReply();
		//LibTransaction tr = req.transaction;
		double value = Double.valueOf(req.value);
		Long accId = Long.valueOf(accountId);
		Account acc = accService.getAccount(accId);
		Appuser user = acc.getUserId();
		
		if(value < 0 && acc.getValue() + value < -Double.valueOf(acc.getCreditLimit())) {
			reply.account = accMapper.fromInternal(acc);
			transService.getByAccountId(accId).forEach(t -> reply.transaction.add(transMapper.fromInternal(t)));
			reply.error_message = "Превышен кредитный лимит! Введите другую сумму для снятия";
			return reply;
		} else if(value > 0 && user.getWallet() - value < 0.0) {
			reply.account = accMapper.fromInternal(acc);
			transService.getByAccountId(accId).forEach(t -> reply.transaction.add(transMapper.fromInternal(t)));
			reply.error_message = "У вас недостаточно средств! Введите другую сумму для пополнения";
			return reply;
		}
		acc.setValue(acc.getValue() + value);
		user.setWallet(user.getWallet() - value);
		accService.addAccount(acc);
		userService.addUser(user);
		
		LibTransaction tr = new LibTransaction();
		tr.accountId = accountId;
		tr.value = value;
		tr.Info = value > 0.0 ? "Пополнения счета из личных средств" : "Вывод средств";
		transService.addTransaction(transMapper.toInternal(tr));
		
		reply.account = accMapper.fromInternal(acc);
		transService.getByAccountId(accId).forEach(t -> reply.transaction.add(transMapper.fromInternal(t)));
		
		return reply;
	}
	
	@RequestMapping(path="/{accountId}/transfer", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public LibTransactionReply transfer(@RequestBody TransferRequest req, @PathVariable String accountId) {
		LibTransactionReply reply = new LibTransactionReply();
		
		//LibTransaction transTo = req.transaction;
		double value = Double.valueOf(req.value);
		Long accId = Long.valueOf(accountId);
		Account accFrom = accService.getAccount(accId);
		if(accFrom.getValue() - value < -Double.valueOf(accFrom.getCreditLimit())) {
			reply.error_message = "Превышен кредитный лимит! Введите другую сумму для перевода";
			reply.account = accMapper.fromInternal(accFrom);
			transService.getByAccountId(accId).forEach(t -> reply.transaction.add(transMapper.fromInternal(t)));
			return reply;
		}
		Account accTo = accService.getAccount(Long.valueOf(req.toAccountId));
		if(accTo == null) {
			reply.error_message = "Такого аккаунта не существует";
			reply.account = accMapper.fromInternal(accFrom);
			transService.getByAccountId(accId).forEach(t -> reply.transaction.add(transMapper.fromInternal(t)));
			return reply;
		}
		try {
			accService.transfer(accFrom, accTo, value);
		} catch (Exception e) {
			reply.error_message = "Ошибка при попытке перевода денег";
			reply.account = accMapper.fromInternal(accFrom);
			transService.getByAccountId(accId).forEach(t -> reply.transaction.add(transMapper.fromInternal(t)));
			return reply;
		}
		LibTransaction transFrom = new LibTransaction();
		transFrom.accountId = accountId;
		transFrom.value = -value;
		transFrom.Info = "Перевод на счет " + req.toAccountId;
		LibTransaction transTo = new LibTransaction();
		transTo.accountId = req.toAccountId;
		transTo.value = value;
		transTo.Info = "Перевод со счета " + accountId;
		
		transService.addTransaction(transMapper.toInternal(transFrom));
		transService.addTransaction(transMapper.toInternal(transTo));
		
		reply.account = accMapper.fromInternal(accFrom);
		transService.getByAccountId(accId).forEach(t -> reply.transaction.add(transMapper.fromInternal(t)));
		
		return reply;
	}
	
}
