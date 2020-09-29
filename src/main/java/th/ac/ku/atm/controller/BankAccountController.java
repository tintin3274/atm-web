package th.ac.ku.atm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import th.ac.ku.atm.model.BankAccount;
import th.ac.ku.atm.service.BankAccountService;

@Controller
@RequestMapping("/bankaccount")
public class BankAccountController {
    private BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping
    public String getBankAccountPage(Model model) {
        model.addAttribute("bankaccounts", bankAccountService.getBankAccounts());
        return "bankaccount";
    }

    @PostMapping
    public String openAccount(@ModelAttribute BankAccount bankAccount, Model model) {
        bankAccountService.openBankAccount(bankAccount);
        model.addAttribute("bankaccounts", bankAccountService.getBankAccounts());
        return "redirect:bankaccount";
    }

    @GetMapping("/delete/{id}")
    public String deleteAccount(@PathVariable int id, Model model) {
        bankAccountService.deleteBankAccount(id);
        model.addAttribute("bankaccounts", bankAccountService.getBankAccounts());
        return "redirect:/bankaccount";
    }

    @GetMapping("/deposit/{id}")
    public String getDepositBankAccountPage(@PathVariable int id, Model model) {
        BankAccount account = bankAccountService.getBankAccount(id);
        model.addAttribute("bankAccount", account);
        model.addAttribute("mode", "Deposit");
        return "bankaccount-edit";
    }

    @GetMapping("/withdraw/{id}")
    public String getWithdrawBankAccountPage(@PathVariable int id, Model model) {
        BankAccount account = bankAccountService.getBankAccount(id);
        model.addAttribute("bankAccount", account);
        model.addAttribute("mode", "Withdraw");
        return "bankaccount-edit";
    }

    @PostMapping("/edit/{id}")
    public String editAccount(@PathVariable int id, @RequestParam double amount,
                                 @RequestParam String mode, Model model) {
        switch (mode){
            case "Deposit": bankAccountService.depositBankAccount(id, amount); break;
            case "Withdraw": bankAccountService.withdrawBankAccount(id, amount); break;
        }
        model.addAttribute("bankaccounts", bankAccountService.getBankAccounts());
        return "redirect:/bankaccount";
    }
}


// **** Error ****
// @ModelAttribute Field error in object 'bankAccount' on field 'id': rejected value [null]
// now fixed temporary by use hardcode
// bankaccount.html
// <input type="hidden" name="id" value="-1"/>
// bankaccount-edit.html
// <input type="hidden" th:field="*{id}"/>
// <input type="hidden" th:field="*{customerId}"/>
// ***************;



//    @GetMapping("/edit/{id}")
//    public String getEditBankAccountPage(@PathVariable int id, Model model) {
//        BankAccount account = bankAccountService.getBankAccount(id);
//        model.addAttribute("bankAccount", account);
//        return "bankaccount-edit";
//    }
//
//    @PostMapping("/edit/{id}")
//    public String editAccount(@PathVariable int id,
//                              @ModelAttribute BankAccount bankAccount,
//                              Model model) {
//
//        bankAccountService.editBankAccount(bankAccount);
//        model.addAttribute("bankaccounts", bankAccountService.getBankAccounts());
//        return "redirect:/bankaccount";
//    }

