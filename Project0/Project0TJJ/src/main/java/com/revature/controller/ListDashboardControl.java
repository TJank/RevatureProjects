package com.revature.controller;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.revature.model.ListItem;
import com.revature.model.User;
import com.revature.model.UserList;
import com.revature.service.ListItemService;
import com.revature.service.ListService;

public class ListDashboardControl {

	public void viewUserLists(User currentUser) {
		Scanner scanLine = new Scanner(System.in);
		ListDashboard listDashboard = new ListDashboard(currentUser);
		ArrayList<UserList> userLists = listDashboard.getCurrentUserLists();
		UserList selectedList = null;
		String userInput = "";
		int count = 0;
		boolean valid = false;
		boolean exit = false;

		System.out.println("---------------------------");
		System.out.println(currentUser.getUsername() + "'s Lists: ");
		// print list titles loop
		for (UserList userList : userLists) {
			count++;
			System.out.println(count + " - " + userList.getTitle());
		}

		while (!valid) {
			System.out.print("Enter 'exit' to go back, or \n" + "Enter list title to perform actions on: ");
			userInput = scanLine.nextLine();

			if (userInput.toLowerCase().equals("exit")) {
				valid = true;
				exit = true;
			} else {
				for (UserList userList : userLists) {
					if (userInput.toLowerCase().equals(userList.getTitle().toLowerCase())) {
						selectedList = userList;
					}
				}
			}
			if (selectedList != null) {
				// continue the loop
				valid = true;
			} else {
				if (!exit) {
					System.out.println("Unknown list title");
					valid = false;
				}
			}
		}
		// user has selected a list to manipulate
		if (selectedList != null) {
			manipulateUserList(currentUser, selectedList);
		}

	}

	private void manipulateUserList(User currentUser, UserList selectedList) {
		// this method is used to add list items, edit list items, share the list, or
		// delete the list!
		int count = 0;
		String userInput = "";
		ListService listService = new ListService(); // service to update, get, delete the whole list
		ListItemService listItemService = new ListItemService(); // service to update or add a single list item to the
																	// current list.
		Scanner scanLine = new Scanner(System.in);
		boolean valid = false; // used to continuously ask for user input to manipulate list
		boolean goBack = false; // used to exit this method and return to the main menu options
		boolean editSuccess = false; // used to determine if edit or save was successful
		boolean delete = false;
		ListItem selectedListItem = null;
		ListItem newListItem = null;
		String editListItem = "";
		int userOption = 0;
		// i need to retrieve this specific list and list items
		UserList completeList = listService.getCompleteUserList(selectedList, currentUser);

		// set sharable option to "Share my list!" if sharable = false, OR to "Make it
		// private.." if sharable = true
		String sharableOption = completeList.isSharable() ? "(3) Make it private..\n" : "(3) Share my list!\n";

		// i need to put the user in a similar loop to be able to manipulate until the
		// use types "exit"
		while (!goBack) {
			System.out.println("---------------------------");
			System.out.println(completeList.getTitle());
			// print list titles loop
			if (completeList.getListItems().get(0).getItemName() != null) {
				for (ListItem listItem : completeList.getListItems()) {
					count++;
					System.out.println(count + " - " + listItem.getItemName());
				}
			}

			System.out.print("Options: \n" + "(1) Add list item\n" + "(2) Edit list\n" + sharableOption
					+ "(4) Delete list\n" + "(5) Exit\n" + "(Ex: enter 1, 'one' or 'add')\n");

			while (!valid) {
				System.out.print("Select option: ");
				userInput = scanLine.nextLine();
				switch (userInput.toLowerCase()) {
				case "1":
				case "one":
				case "add":
					// add an item
					// prompt user to add a new list item name
					System.out.print("Enter a new list item: ");

					// store the list item name & create a new list item
					editListItem = scanLine.nextLine();
					newListItem = new ListItem(editListItem, completeList.getId());
					// call method to insert the new list item to db and to join table
					listItemService.insertNewListItem(newListItem);
					valid = true;
					break;

				case "2":
				case "two":
				case "edit":
					System.out.print("Enter list item name to edit: ");
					editListItem = scanLine.nextLine();
					for (ListItem listItem : completeList.getListItems()) {
						if (editListItem.toLowerCase().equals(listItem.getItemName().toLowerCase())) {
							selectedListItem = listItem;
							valid = true;
						}

					}
					if (selectedListItem != null) {
						// user selected a correct list item to edit
						// edit list item then saves the changes to DB
						System.out.print("Enter new list item: ");
						userInput = scanLine.nextLine(); // take in the new list item name
						newListItem = new ListItem(selectedListItem.getId(), userInput, completeList.getId());

						// make a call to update the list item in db
						listItemService.updateUserListItem(completeList, newListItem);

						valid = true;
					} else {
						System.out.println("Unknown list name title");
						valid = false;
					}
					break;

				case "3":
				case "three":
				case "share":
					valid = true;
					// make the list sharable OR private -- sharable = false
					if (completeList.isSharable()) {
						completeList.setSharable(false);
					} else {
						completeList.setSharable(true);
					}
					listService.updateUserList(completeList);
					break;

				case "4":
				case "four":
				case "delete":
					// delete the list
					// ask for confirmation & delete!
					System.out.print("Are you sure you want to delete this list?\n" + "(1) Yes! Delete already..\n"
							+ "(2) No, changed my mind..\n");
					while (!valid) {
						try {
							System.out.print("Enter a 1 or 2 : "); // cursor is here
							userOption = scanLine.nextInt();
							while (userOption > 3 || userOption < 1) {
								System.out.print("Enter a 1 or 2 : ");
								userOption = scanLine.nextInt();
							}
							valid = true;
						} catch (InputMismatchException e) {
							System.out.println("Error digits only please.");
							scanLine.nextInt(); // clear bad data
						}
					}
					if (userOption == 1) {
						// delete the list
						delete = true;
						goBack = true;
						listService.deleteUserList(completeList, currentUser);
					}

					break;

				case "5":
				case "five":
				case "exit":
					// set loop to false to exit this application
					valid = true;
					goBack = true;
					break;
				default:
					System.out.println("Unknown option..");
					break;

				}
			}
			if (!delete) {
				completeList = listService.getCompleteUserList(completeList, currentUser);
				sharableOption = completeList.isSharable() ? "(3) Make it private..\n" : "(3) Share my list!\n";
			}
			newListItem = null;
			count = 0;
			valid = false; // reset valid to re-show options and allow user to continue manipluating list
		}

	}

	public void viewAllSharableLists(User currentUser) {
		ListService listService = new ListService();
		Scanner scanLine = new Scanner(System.in);
		ArrayList<UserList> publicLists = listService.getAllSharableLists();
		UserList selectedList = null;
		String consoleString = "";
		String userInput = "";
		int count = 0;
		boolean valid = false;
		boolean exit = false;

		while (!exit) {
			System.out.println("---------------------------");
			System.out.println("Public Lists: ");
			// print list titles loop
			for (UserList userList : publicLists) {
				count++;
				System.out.println(count + " - " + userList.getTitle());
			}
			while (!valid) {
				System.out.print("Enter 'exit' to go back, or\n" + "Enter list title to view: ");
				userInput = scanLine.nextLine();

				if (userInput.toLowerCase().equals("exit")) {
					valid = true;
					exit = true;
				} else {
					for (UserList userList : publicLists) {
						if (userInput.toLowerCase().equals(userList.getTitle().toLowerCase())) {
							selectedList = userList;
						}
					}
				}
				if (selectedList != null) {
					// continue the loop
					valid = true;
				} else {
					if (!exit) {
						System.out.println("Unknown list title");
						valid = false;
					}
				}
			}
			// user has selected a valid list title to view
			valid = false; // reset to enter next loop and scan for user input
			count = 0;
			while (selectedList != null) {
				System.out.println("---------------------------");
				System.out.println(selectedList.getTitle() + ":");
				// print list titles loop
				if (selectedList.getListItems().get(0).getItemName() != null) {
					for (ListItem listItem : selectedList.getListItems()) {
						count++;
						System.out.println(count + " - " + listItem.getItemName());
					}
				}
				while (!valid) {
					System.out.print(
							"Would you like to add this list to your lists?\n" + "(1) Yes!\n" + "(2) No / go back\n"
									+ "Option: ");
					userInput = scanLine.nextLine();
					switch (userInput.toLowerCase()) {
					case "1":
					case "one":
					case "yes":
						valid = true;
						exit = true;
						if(((Integer) selectedList.getUserId()) == ((Integer)currentUser.getId())) {
							System.out.println("You own that list!");
							exit = false;
						} else {
							// user does not own that list so make a copy
							listService.insertSharedList(selectedList, currentUser);
						}
						
						
						selectedList = null;
						break;

					case "2":
					case "two":
					case "no":
					case "go back":
						valid = true;
						selectedList = null;
						System.out.println("User selected 'go back'");
						break;
					default:
						System.out.println("Unknown option..");
						break;
					}
				}
			}
		}
	}

}
