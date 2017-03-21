package com.mycompany.client.bank.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.client.bank.api.LibAccount;
import com.mycompany.client.bank.api.LibBankReply;
import com.mycompany.client.bank.api.LibTransaction;
import com.mycompany.client.bank.api.LibTransactionReply;
import com.mycompany.client.bank.api.PostRequest;
import com.mycompany.client.bank.jpa.Account;
import com.mycompany.client.bank.jpa.Appuser;
import com.mycompany.client.bank.services.AccountMapper;
import com.mycompany.client.bank.services.AccountService;
import com.mycompany.client.bank.services.AppUserAndUserDetailsService;
import com.mycompany.client.bank.services.BankMapper;
import com.mycompany.client.bank.services.BankService;
import com.mycompany.client.bank.services.TransactionMapper;
import com.mycompany.client.bank.services.TransactionService;

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
	
	@RequestMapping(path="/{accountId}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public LibTransactionReply getAccountInfoAndTransactions(@PathVariable String accountId) {
		
		LibTransactionReply reply = new LibTransactionReply();
		Long id = Long.valueOf(accountId);
		
		reply.account = accMapper.fromInternal(accService.getAccount(id));
		
		transService.getByAccountId(id).forEach(tr -> reply.transaction.add(transMapper.fromInternal(tr)));
		
		return reply;
	}
	
	@RequestMapping(path="/{userId}/{bankId}", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public LibTransactionReply createAccount(@PathVariable String userId, @PathVariable String bankId) {
		LibTransactionReply reply = new LibTransactionReply();
		
		LibAccount libAcc = new LibAccount();
		libAcc.bankId = Long.valueOf(bankId);
		libAcc.userId = Long.valueOf(userId);
		libAcc.value = 0.0;
		
		Account acc = accMapper.toInternal(libAcc);
		accService.addAccount(acc);
		
		reply.account = accMapper.fromInternal(acc);
		return reply;
	}
	
	@RequestMapping(path="/banks", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public LibBankReply getBanks() {
		LibBankReply reply = new LibBankReply();
		
		bankService.getAll().forEach(b -> reply.banks.add(bankMapper.fromInternal(b)));
		
		return reply;
	}
	
	@RequestMapping(path="/{accountId}/transaction", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public LibTransactionReply changeWallet(@RequestBody PostRequest req, @RequestBody String accountId) {
		LibTransactionReply reply = new LibTransactionReply();
		LibTransaction tr = req.transaction;
		Long accId = Long.valueOf(accountId);
		Account acc = accService.getAccount(accId);
		Appuser user = acc.getUserId();
		
		if(tr.value < 0 && acc.getValue() + tr.value < -4000.0) {
			reply.error_message = "Превышен кредитный лимит! Введите другую сумму для снятия";
			return reply;
		} else if(tr.value > 0 && user.getWallet() - tr.value < 0) {
			reply.error_message = "У вас недостаточно средств! Введите другую сумму для пополнения";
			return reply;
		}
		acc.setValue(acc.getValue() + tr.value);
		user.setWallet(user.getWallet() - tr.value);
		accService.addAccount(acc);
		userService.addUser(user);
		
		tr.accountId = accId;
		transService.addTransaction(transMapper.toInternal(tr));
		
		reply.account = accMapper.fromInternal(acc);
		transService.getByAccountId(accId).forEach(t -> reply.transaction.add(transMapper.fromInternal(t)));
		
		return reply;
	}
	
}
