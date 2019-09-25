package com.revature;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.revature.controller.Controller;
import com.revature.controller.ListDashboardControl;
import com.revature.model.User;

// This class runs the List Application
public class Driver {

	public static void main(String[] args) {
		boolean on = true; // infinite loop to run the application - false to end application normally
		boolean exit = false;
		Scanner scanIn = new Scanner(System.in); // reads the int value for user option
		Scanner scanMenu = new Scanner(System.in);
		Controller control = new Controller(); // controller for create user, login user, and create list
		ListDashboardControl listDashBoardControl = new ListDashboardControl(); // controller for the list dashboard
		boolean valid = false; // used to test for valid user input option
		boolean innerCondition = false;
		int option = 0; // stores the user's input option
		int menuOption = 0;
		User currentUser = null; // used to test if a user is logged in and to pass between operations
		boolean isLoggedIn = false;

		while (on) {
			/*
			 * first test if the user is logged in if no user is logged in constantly ask
			 * for user to log in or create user.
			 */
			while (!exit) {
				while (!isLoggedIn) {

					// prompt for user to create user / login THEN STEP into the while loop
					System.out.print("Select an option: \n" + "(1) Create User\n" + "(2) Login\n" + "(3) Exit \n");

					while (!valid) {
						try {
							System.out.print("Enter a 1, 2, or 3 : "); // cursor is here
							option = scanIn.nextInt();
							while (option > 3 || option < 1) {
								System.out.print("Enter a 1, 2, or 3: ");
								option = scanIn.nextInt();
							}
							valid = true;
						} catch (InputMismatchException e) {
							System.out.println("Error digits only please.");
							scanIn.next(); // clear bad data
						}
					}
					// we have a valid option : 1, 2, or 3
					switch (option) {
					case 1:
						// create user
//					System.out.println(option);
						control.createUser();
						option = 0;
						valid = false;
						break;

					case 2:
						// login
						currentUser = control.logUserIn();
						if (currentUser != null) {
							isLoggedIn = true;
//						System.out.print("Welcome " + currentUser.getUsername() + "! ");
						}
						option = 0;
						valid = false;
						break;

					case 3:
						// exit app
						isLoggedIn = true;
						currentUser = null;
						valid = true;
						on = false;
						exit = true;
						break;

					default:
						break;
					}
					// reset here because we aren't logged in yet?
				}
//			 if a user is logged in we allow them to use the  application
				if (!exit) {
					if (isLoggedIn) {
						// Display menu options
						System.out.print("What would you like to do?\n" + "(1) View Lists.\n"
								+ "(2) Create a new list.\n" + "(3) View Public Lists.\n" + "(4) Logout.\n");
						// test for input and decide what to do based on input
						while (!innerCondition) {
							try {
								System.out.print("Enter a 1, 2, 3, or 4 : "); // cursor is here
								menuOption = scanMenu.nextInt();
								while (menuOption > 4 || menuOption < 1) {
									System.out.print("Enter 1, 2, 3, or 4 : ");
									menuOption = scanMenu.nextInt();
								}
								innerCondition = true;
							} catch (InputMismatchException e) {
								System.out.println("Error digits only please.");
								scanMenu.next(); // clear bad data
							}
						}
						// we have a valid option : 1, 2, or 3
						switch (menuOption) {
						case 1:
							// View Lists:
							listDashBoardControl.viewUserLists(currentUser);
							break;

						case 2:
							// Create a new list
							control.createNewList(currentUser);
							break;

						case 3:
							// view sharable lists
							listDashBoardControl.viewAllSharableLists(currentUser);
							break;

						case 4:
							// exit app
							isLoggedIn = false;
							currentUser = null;
							on = false;
						default:
							break;
						}
						menuOption = 0;
						innerCondition = false;
					}
				}
			}
		}
	}
}
