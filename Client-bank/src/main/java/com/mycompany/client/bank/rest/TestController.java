/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client.bank.rest;

import com.mycompany.client.bank.api.PostRequest;
import com.mycompany.client.bank.api.LibBankReply;
import com.mycompany.client.bank.api.LibTransactionReply;
import com.mycompany.client.bank.jpa.Bank;
import com.mycompany.client.bank.jpa.Transaction;
import com.mycompany.client.bank.services.BankMapper;
import com.mycompany.client.bank.services.BankService;
import com.mycompany.client.bank.services.TransactionMapper;
import com.mycompany.client.bank.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author artem
 */
@RestController
public class TestController {
      @Autowired
    TransactionService trnService;
    @Autowired
    BankService bnkService;
    @Autowired
    TransactionMapper trnMapper;
    @Autowired
    BankMapper bnkMapper;
	//checkAuthorization()	
        //RegisrateNewUser()
        //GetInfoForUserPage()
        //Notification()
    @RequestMapping(path="/transactions/add",  method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public LibTransactionReply AddTransaction(@RequestBody PostRequest req){
        LibTransactionReply reply = new LibTransactionReply();
           try{
           Transaction tr;
           tr = trnService.addTransaction(trnMapper.toInternal(req.transaction));
           reply.transaction.add(trnMapper.fromInternal(tr));
        }catch(Exception e){
            reply.retcode = -1;
            reply.error_message = e.getMessage();
           // logger.error("Error adding user. Expetion: "+e.getMessage(),e);
        }
        return reply;
    }
    @RequestMapping(path="/banks/add",  method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public LibBankReply AddBank(@RequestBody PostRequest req){
        LibBankReply reply = new LibBankReply();
           try{
           Bank bank;
           bank = bnkService.addBank(bnkMapper.toInternal(req.bank));
           reply.banks.add(bnkMapper.fromInternal(bank));
        }catch(Exception e){
            reply.retcode = -1;
            reply.error_message = e.getMessage();
           // logger.error("Error adding user. Expetion: "+e.getMessage(),e);
        }
        return reply;
    }
}
