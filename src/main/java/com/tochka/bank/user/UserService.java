package com.tochka.bank.user;

import com.tochka.bank.account.Account;
import com.tochka.bank.account.AccountService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class UserService {

    private final Map<Integer, User> userMap;
    private Set<String> loginsSet;
    private AccountService accountService;

    private int idCounter;

    public UserService(AccountService accountService) {
        this.accountService = accountService;
        this.loginsSet = new HashSet<>();
        this.userMap = new HashMap<>();
        this.idCounter = 0;
    }

    public User createUser(String login) {
        if (loginsSet.contains(login)) {
            throw new IllegalArgumentException("User with this login = %s is already exist".formatted(login));
        }
        loginsSet.add(login);

        idCounter++;
        User newUser = new User(idCounter, login, new ArrayList<>());
        Account newAccount = accountService.createAccount(newUser);
        newUser.getAccountList().add(newAccount);
        userMap.put(newUser.getId(), newUser);
        return newUser;
    }

    public Optional<User> findUserById(int id) {
        return Optional.ofNullable(userMap.get(id));
    }

    public List<User> getAllUsers() {
        return userMap.values().stream().toList();
    }
}
