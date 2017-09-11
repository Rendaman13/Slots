import java.io.*; // Imports everything from java.io. Necessary for reading and writing save, as well as throwing IOException
import javax.swing.JOptionPane; // imports JOptionPane, necessary for dialog boxes
import java.util.concurrent.ThreadLocalRandom; // Imports rng
public class SlotsUpdated {
	public static void main(String[] args) throws IOException { // Main loop, throws IOException to prevent error with buffered reader and writer
		JOptionPane.showMessageDialog(null, "Welcome to Slots","Slots",JOptionPane.PLAIN_MESSAGE); // Opens dialog "Welcome to slots"
		File save = new File(System.getProperty("user.home") + "/Documents/SlotsSave.txt"); // Writes directory for save
		int money = 500; // Initializes money at 500
		int bet = 0; // Initializes bet at 0
		int sevencounter = 0; // Initializes sevencounter at 0
		String betS = null; // Initializes betS as null
		int betcounter = 0; // Initializes betcounter at 0
		boolean keepplaying = true; // Initializes keepplaying variable at true, allows while loop to persist
		int randomInt1; // Initializes 3 random variables at null
		int randomInt2;
		int randomInt3;
		String betcounterS; // Initializes betcounterS at null, necessary for money and betcounter to be string to write to save file
		String moneyS; // Initializes moneyS as null, see above
		boolean usedsave = false; // Initializes usedsave at false, used to tell if user restored from save
		int min = 1; // Minimum rng value
		int max = 9; // Maximum rng value
		BufferedReader br; // Initializes BufferedReader
		BufferedWriter bw; // Initializes BufferedWriter
		int doesrestore; // Integer, used when the program asks whether or not user would like to restore from save
		int leaveorstay; // Integer, used when the program asks whether or not user would like to save and quit
		Object[] leaveorstaybuttons = {"Keep Playing","Save and quit"}; // Buttons, used for leaveorstay dialog
		Object[] restoresave = {"Restore from save","Start from scratch"}; // Buttons, used for doesrestore dialog
		if(save.exists()) { //If the save file exists, do this...
			doesrestore = JOptionPane.showOptionDialog(null, "Restore from save?", "Slots", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, restoresave, restoresave[1]); //Dialog, asks user if user would like to restore from save or start from scratch. Saves input to doesrestore.
			if (doesrestore == 0) { // If the user decides to resotre, do this..
				br = new BufferedReader(new FileReader(save)); // br is now a BufferedReader
				try {
					money = Integer.parseInt(br.readLine()); // Checks integer parsing for first line
					betcounter = Integer.parseInt(br.readLine());// Checks integer parsing for second line
					usedsave = true; // If successful, usedsave is true
				} catch (NumberFormatException nex) { // Catches NumberFormatException, save file is likely corrupted
					JOptionPane.showMessageDialog(null, "The save file has been corrupted","Slots",JOptionPane.ERROR_MESSAGE); // Informs user save is corrupted
					money = 500; // Starts from scratch regardless of user decision
					betcounter = 0;
				}
				br.close(); // Closes filereader, prevents resource leak
			}
		}
		while (keepplaying) { // Main game loop
			JOptionPane.showMessageDialog(null,"You have " + money + " money and have betted " + betcounter + " time(s)","Slots",JOptionPane.INFORMATION_MESSAGE); // Tells your money count as well as the number of times you've bet
			randomInt1 = ThreadLocalRandom.current().nextInt(min, max + 1); //Generates 3 random numbers
			randomInt2 = ThreadLocalRandom.current().nextInt(min, max + 1);
			randomInt3 = ThreadLocalRandom.current().nextInt(min, max + 1);
			betS = JOptionPane.showInputDialog("How much do you want to bet?"); //Asks for bet. THIS IS SAVED AS A STRING, IT MUST BE PARSED AS AN INTEGER OR NOTHING WILL WORK!!!
			while (!IsBetInteger(betS)) {// If integer parsing fails, user likely inputed a string, user is likely an imbecile
				JOptionPane.showMessageDialog(null, "Invalid bet! -2 bucks for programmer inconvience!","Slots",JOptionPane.ERROR_MESSAGE); // Berates user for being an imbecile
				money -= 2; //Subtracts 2 dollars
				betS = JOptionPane.showInputDialog("How much do you want to bet?"); // Asks for bet
			}
			bet = Integer.parseInt(betS); // Parses bet as integer
			while (bet > money || bet < 1) { // If bet is out of range...
				JOptionPane.showMessageDialog(null, "No, silly! You can't bet that!","Slots",JOptionPane.WARNING_MESSAGE); // Informs user of mistake
				betS = JOptionPane.showInputDialog("How much do you want to bet?"); // Asks for bet
				while (!IsBetInteger(betS)) { // Runs bet through Integer parsing error handler, if fails, same protocol as above
					JOptionPane.showMessageDialog(null, "Invalid bet! -2 bucks for programmer inconvience!","Slots",JOptionPane.ERROR_MESSAGE);
					money -= 2;
					betS = JOptionPane.showInputDialog("How much do you want to bet?");
				}
				bet = Integer.parseInt(betS); // Parses bet as integer
			}
			if (randomInt1 == 7) { //If the first random int is equal to seven...
				sevencounter += 1; // ...then add 1 to the sevencounter
			}
			if (randomInt2 == 7) { // Same protocol
				sevencounter += 1;
			}
			if (randomInt3 == 7) { //Same protocol
				sevencounter += 1;
			}
			money -= bet; // Subtract bet from money
			betcounter += 1; // Add 1 to betcounter
			JOptionPane.showMessageDialog(null, randomInt1 + " " + randomInt2 + " " + randomInt3,"Slots",JOptionPane.INFORMATION_MESSAGE); // Display 3 random numbers
			if (sevencounter == 1) { //If sevencounter is 1
				JOptionPane.showMessageDialog(null, "You got one seven! Double your bet!","Slots",JOptionPane.INFORMATION_MESSAGE);
				money += bet * 2; // Add doubled bet to money count
			} else if (sevencounter == 2) { // Otherwise, is sevencounter is 2
				JOptionPane.showMessageDialog(null,"Wow! Two sevens! Triple your bet!","Slots",JOptionPane.INFORMATION_MESSAGE);
				money += bet * 3; // Add tripled bet to money count
			} else if (sevencounter == 3) { //Otherwise, if sevencounter is 3
				JOptionPane.showMessageDialog(null,"JACKPOT!!! Your money count has been tripled!!!!","Slots",JOptionPane.INFORMATION_MESSAGE);
				money *= 3; // Triple money count
			} else {
				JOptionPane.showMessageDialog(null, "Sorry, no sevens","Slots",JOptionPane.INFORMATION_MESSAGE); // Insult to injury >:)
			}
			if ((randomInt2 - randomInt1 == 1 && randomInt3 - randomInt2 == 1) || (randomInt1 - randomInt2 == 1 && randomInt2 - randomInt3 == 1)) { //Checks for sequences, like 5-4-3 or 6-7-8
				JOptionPane.showMessageDialog(null, "SEQUENTIAL BONUS!!!!!! Your money count has been doubled!");
				money *= 2; // Doubles money count
			}
			if (money < 1) { //Checks if user is out of money
				JOptionPane.showMessageDialog(null, "You ran out of money! GAMEOVER","Slots",JOptionPane.ERROR_MESSAGE);
				keepplaying= false; // Sets to false to end while loop
				if (usedsave) { //If user restored from save...
					save.delete(); // ...then wipe the save
				}
			} else { //If user still has money
			leaveorstay = JOptionPane.showOptionDialog(null,"Keep playing or save and quit?","Slots",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,leaveorstaybuttons,leaveorstaybuttons[1]); //Ask if user would still like to play
			if (leaveorstay != 0) { //If user decides to leave...
				if (!save.exists()) { // ...then first, check if save doesn't exist...
					save.createNewFile(); // ...and create a new one if missing
				}
				bw = new BufferedWriter(new FileWriter(save)); // bw is now a working filewriter
				moneyS = Integer.toString(money); // Converts money to string to prevent corruption of save
				betcounterS = Integer.toString(betcounter); // Repeats for betcounter
				bw.write(moneyS); // Writes money to first line of save
				bw.newLine(); //Writes newline. Surprisingly enough, "\n" does not have the same effect
				bw.write(betcounterS); // Write betcounter to second line of save
				bw.close(); //Close BufferedWriter to prevent resource leak
				JOptionPane.showMessageDialog(null, "Ok. You had " + money + " money. Your game has been saved.","Slots",JOptionPane.INFORMATION_MESSAGE);
				keepplaying = false; // Allows while loop to end
			}
			sevencounter = 0; // Resets sevencounter for next loop
			}
	}
 }
	public static boolean IsBetInteger(String betS) { //Error handler for integer parsing of betS
		try {
			Integer.parseInt(betS); //Parse betS
			return true;// return true if successful
		} catch (NumberFormatException ex2) {
			return false; //Return false if NumberFormatException
		}
	}
}
