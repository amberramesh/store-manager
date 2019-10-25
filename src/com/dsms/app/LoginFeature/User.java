/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dsms.app.LoginFeature;


public class User {
    private static String employeeID;
    private static String firstName;
    private static String lastName;
    private static int isAdmin;
    private static int isManager;

    public User(String employeeID, String firstName, String lastName,int isAdmin,int isManager) {
        User.employeeID=employeeID;
        User.firstName=firstName;
        User.lastName=lastName;
        User.isAdmin=isAdmin;
        User.isManager=isManager;
    }

    public static String getEmployeeID() {
        return employeeID;
    }

    public static void setEmployeeID(String employeeID) {
        User.employeeID = employeeID;
    }

    public static String getFirstName() {
        return firstName;
    }

    public static void setFirstName(String firstName) {
        User.firstName = firstName;
    }

    public static String getLastName() {
        return lastName;
    }

    public static void setLastName(String lastName) {
        User.lastName = lastName;
    }

    public static int getIsAdmin() {
        return isAdmin;
    }

    public static void setIsAdmin(int isAdmin) {
        User.isAdmin = isAdmin;
    }

    public static int getIsManager() {
        return isManager;
    }

    public static void setIsManager(int isManager) {
        User.isManager = isManager;
    }
    
    
}
