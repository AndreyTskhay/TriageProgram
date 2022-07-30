import java.util.Scanner;

/*This program works as a triage system that uses a binary tree to sort patients
 * based on some simple questions about the patient current condition */

public class Triage_Driver {
	public static void main(String [] args) {
		//Create a binary tree and the triage questions with the answers.
		
		LinkedBinaryTree<String> triage = new LinkedBinaryTree<>();
		
		System.out.println("***Need some triage?***");
		
		//each branch of the tree either leads to the answer or leads to another questions
		
		Position<String> root = triage.addRoot("Can he/she walk? (Yes/No)");
		Position<String> breathing = triage.addLeft(root, "Is he/she breathing? (Yes/No)");
		triage.addRight(root, "Delayed care");
		
		Position<String> breathingAirway = triage.addLeft(breathing, "Open patient's airway. Is patient breathing? (Yes/No)");
		triage.addLeft(breathingAirway, "Unsalvageable");
		triage.addRight(breathingAirway, "Immediate Care");
		
		Position<String> countRespirations = triage.addRight(breathing, "Is count more or equal to 30?");
		triage.addLeft(countRespirations, "Immediate care");
		Position<String> radialPulse = triage.addRight(countRespirations, "is radial pulse present? (Yes/No)");
		
		triage.addLeft(radialPulse, "Immediate care");
		Position<String> mentalStatus = triage.addRight(radialPulse, "Assess mental status. Can patient follow commands?(Yes/No)");
		triage.addLeft(mentalStatus, "Immediate care");
		triage.addRight(mentalStatus, "Delayed Care");
		
		//preparing input from a user
		Scanner input = new Scanner(System.in);
		String inputString = "";
		Position<String> current = triage.root();
		System.out.println(current.getElement());
		
		//accept input until the program gives an ultimate answer
		do {
			inputString = input.next();
			if(inputString.trim().toLowerCase().equals("yes")) {
				current = triage.right(current);
				System.out.println(current.getElement());
			}
			else if(inputString.trim().toLowerCase().equals("no")) {
				current = triage.left(current);
				System.out.println(current.getElement());
			}
			else System.out.println("Incorrect input, please enter either YES or NO");
			if(triage.isExternal(current))
				break;
		}
		while(current!=null);
		
		System.out.println("***\nEnd of Program***");
		input.close();
		
		//uncomment the line below to see the whole tree
		//System.out.println("Tree output:\n" + triage.toString());
	}
}