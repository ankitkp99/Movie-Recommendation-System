package com.app.clientapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.app.model.UserModel;
import com.app.services.AdminServices;
import com.app.services.AdminServicesImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MovieRecommendationSystem {
    static String userName;

    // Logger instance
    private static final Logger logger = LogManager.getLogger(MovieRecommendationSystem.class);

    public static String getUsername() {
        return userName;
    }

    public static void callMain() {
        String args[] = {};
        main(args);
    }

    public static void main(String[] args) {

    	System.out.println("\n\n\n\t\t\t\t\t\t 𝙈𝙤𝙫𝙞𝙚 𝙍𝙚𝙘𝙤𝙢𝙢𝙚𝙣𝙙𝙖𝙩𝙞𝙤𝙣 𝙎𝙮𝙨𝙩𝙚𝙢 \n\n\n");
        logger.info("Movie Recommendation System started");
        UserModel userModel;
        AdminServices adminService = new AdminServicesImpl();
        Scanner sc = ScannerClass.getScanner();
        String choice;

        do {
            logger.info("Prompting user for action");
            System.out.println("----------------------------------");
			System.out.println("========    Login Page    ========");
			System.out.println("----------------------------------");
            System.out.println("1:Login \n2:Register\n3:Exit\nEnter your choice");
            choice = sc.nextLine();

            switch (choice) {
                case "1":
                    logger.info("Login option selected");

                    // Log user input actions
                    System.out.println("Enter username");
                    String username = sc.nextLine();
                    logger.debug("Username entered: {}", username);

                    System.out.println("Enter password ");
                    String password = sc.nextLine();
                    logger.debug("Password entered for user: {}", username);

                    List<String> loginInfo = new ArrayList<>(adminService.getNameByLogin(username, password));

                    if (!loginInfo.isEmpty()) {
                        if (loginInfo.get(1).equals("Admin")) {
                            logger.info("Admin login successful for user: {}", loginInfo.get(0));
                            System.out.println("\n***** Welcome back " + loginInfo.get(0) + " *****\n");
                            userName = username;
                            Admin.AdminFunctinality();
                        } else {
                            logger.info("User login successful for user: {}", loginInfo.get(0));
                            System.out.println("\n***** Welcome back " + loginInfo.get(0) + " *****\n");
                            userName = username;
                            User.userFunctionality();
                        }
                    } else {
                        logger.warn("Login failed for username: {}", username);
                        System.out.println("\n===== !!! You Have To Register First !!! =====\n");
                    }
                    break;

                case "2":
                    logger.info("Registration option selected");
                    System.out.println("------------------------------");
        			System.out.println("=======  Registration  =======");
        			System.out.println("------------------------------");
                    String userChoice;
                    String rePassword;
                    System.out.println("1:Admin Registration\n2:User Registration\n\nEnter your choice");
                    userChoice = sc.nextLine();

                    // Log registration process
                    System.out.println("\nEnter your name");
                    String name = sc.nextLine();
                    logger.debug("Name entered: {}", name);

                    System.out.println("\nEnter your email");
                    String email = sc.nextLine();
                    logger.debug("Email entered: {}", email);

                    while ((adminService.getUserEmail()).contains(email)) {
                        logger.warn("Email already registered: {}", email);
                        System.out.println("Email already registered use another (𝓟𝓻𝓮𝓼𝓼 0 𝓯𝓸𝓻 𝓔𝔁𝓲𝓽)");
                        email = sc.nextLine();
                        if (email.equals("0")) {
                            logger.info("Registration terminated by user.");
                            System.out.println("!!! Session Terminated !!!");
                            MovieRecommendationSystem.callMain();
                        }
                    }

                    // Additional input checks and logging
                    System.out.println("\nEnter your contact");
                    String contact = sc.nextLine();
                    logger.debug("Contact entered: {}", contact);
                    while ((adminService.getUserContact()).contains(contact)) {
                        logger.warn("Contact already registered: {}", contact);
                        System.out.println("Contact already registered use another (𝓟𝓻𝓮𝓼𝓼 0 𝓯𝓸𝓻 𝓔𝔁𝓲𝓽)");
                        contact = sc.nextLine();
                        if (contact.equals("0")) {
                            logger.info("Registration terminated by user.");
                            System.out.println("!!! Session Terminated !!!");
                            MovieRecommendationSystem.callMain();
                        }
                    }

                    System.out.println("\nEnter your username");
                    username = sc.nextLine();
                    logger.debug("Username entered: {}", username);
                    while ((adminService.getUsername()).contains(username)) {
                        logger.warn("Username already taken: {}", username);
                        System.out.println("Username is unavailable use another (𝓟𝓻𝓮𝓼𝓼 0 𝓯𝓸𝓻 𝓔𝔁𝓲𝓽)");
                        username = sc.nextLine();
                        if (username.equals("0")) {
                            logger.info("Registration terminated by user.");
                            System.out.println("!!! Session Terminated !!!");
                            MovieRecommendationSystem.callMain();
                        }
                    }

                    System.out.println("\nEnter your password (must between 6-16 letters)");
                    password = sc.nextLine();
                    logger.debug("Password entered for user: {}", username);

                    if (name.matches("[a-zA-Z ]+") && email.matches("^[a-z0-9.]+@[a-z]+\\.[a-z]{2,3}$")
                            && contact.matches("[0-9]+") && contact.length() == 10
                            && (password.length() > 5 && password.length() < 17)) {
                        boolean flag = false;
                        while (true) {
                            System.out.println("\nRe-enter your password (𝓟𝓻𝓮𝓼𝓼 0 𝓯𝓸𝓻 𝓔𝔁𝓲𝓽)");
                            rePassword = sc.nextLine();
                            if (password.equals(rePassword)) {
                                flag = true;
                                break;
                            } else if (rePassword.equals("0")) {
                                break;
                            } else {
                                logger.warn("Passwords do not match for user: {}", username);
                                System.out.println("\n===== !!! Wrong Password !!! =====\n");
                            }
                        }

                        if (flag) {
                            switch (userChoice) {
                                case "1":
                                    logger.info("Admin registration initiated for user: {}", username);
                                    System.out.println("\nEnter security code");
                                    if (sc.nextLine().equals("1234")) {
                                        userModel = new UserModel(name, email, contact, username, password, "", "Admin");
                                        System.out.println(adminService.isRegister(userModel) ? "Admin Registered Successfully" : "Admin Registration Failed");
                                    } else {
                                        logger.warn("Wrong security code for admin registration");
                                        System.out.println("\n===== !!! Wrong Security Code !!! =====\n Enter again security code");
                                        if (sc.nextLine().equals("1234")) {
                                            userModel = new UserModel(name, email, contact, username, password, "", "Admin");
                                            System.out.println(adminService.isRegister(userModel) ? "Admin Registered Successfully" : "Admin Registration Failed");
                                        } else {
                                            logger.warn("Admin registration failed due to wrong security code.");
                                            System.out.println("\n===== !!! Wrong Security Code !!! =====\n");
                                        }
                                    }
                                    break;

                                case "2":
                                    userModel = new UserModel(name, email, contact, username, password, "", "User");
                                    System.out.println(adminService.isRegister(userModel) ? "\n***** " + name + " You Are Registered Successfully *****\n" 
                                    		: "\n===== !!! Registration Failed !!! =====\n");
                                    break;

                                default:
                                    logger.warn("Invalid registration choice: {}", userChoice);
                                    System.out.println("\n===== !!! Invalid Choice !!! =====\n");
                            }
                        }
                    } else {
                        logger.warn("Invalid input for registration.");
                        System.out.println("\n===== !!! Invalid Input !!! =====\n");
                    }
                    break;

                case "3":
                    logger.info("Session terminated by user");
                    System.out.println("\n***** Session Terminated *****\n");
                    System.exit(0);

                default:
                    logger.warn("Invalid menu choice: {}", choice);
                    System.out.println("\n===== !!! Invalid Input !!! =====\n");
                    break;
            }
        } while (!choice.equals("3"));
    }
}
