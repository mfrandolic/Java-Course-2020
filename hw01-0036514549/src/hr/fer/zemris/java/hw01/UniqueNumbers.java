package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Razred modelira uređeno binarno stablo i sadrži neke osnovne
 * metode za rad s njime.
 * 
 * @author Matija Frandolić
 * @version 1.0
 */
public class UniqueNumbers {

	/**
	 * Predstavlja čvor uređenog binarnog stabla. Sadrži
	 * reference na lijevo i desno dijete te čuva jednu 
	 * <code>int</code> vrijednost.
	 */
	public static class TreeNode {
		public TreeNode left;
		public TreeNode right;
		public int value;
	}

	/**
	 * Glavni program koji demonstrira rad s uređenim binarnim stablom.
	 * Korisnik upisuje cijele brojeve koji se dodaju u stablo samo ako 
	 * već ne postoje. Unos se prekida kad korisnik upiše "kraj".
	 * 
	 * @param args argumenti iz komandne linije
	 */
	public static void main(String[] args) {
		TreeNode root = null;
		Scanner scanner = new Scanner(System.in);
		
		while (true) {
			System.out.print("Unesite broj > ");
			if (scanner.hasNextInt()) {
				int number = scanner.nextInt();
				if (containsValue(root, number)) {
					System.out.println("Broj već postoji. Preskačem.");
				} else {
					root = addNode(root, number);
					System.out.println("Dodano.");
				}
			} else {
				String inputString = scanner.next();
				if (inputString.equals("kraj")) {
					break;
				} else {
					System.out.printf("'%s' nije cijeli broj.%n", inputString);					
				}
			}
		}
		
		System.out.print("Ispis od najmanjeg: ");
		printAscending(root);
		
		System.out.println();
		
		System.out.print("Ispis od najvećeg: ");
		printDescending(root);
		
		scanner.close();
	}

	/**
	 * Dodaje novi čvor koji će sadržavati predanu vrijednost u stablo.
	 * 
	 * @param node	korijen stabla
	 * @param value vrijednost koja se zapisuje u čvor
	 * @return		novi čvor
	 */
	public static TreeNode addNode(TreeNode node, int value) {
		if (node == null) {
			TreeNode newNode = new TreeNode();
			newNode.left = null;
			newNode.right = null;
			newNode.value = value;
			return newNode;
		} else if (value < node.value) {
			node.left = addNode(node.left, value);
		} else if (value > node.value) {
			node.right = addNode(node.right, value);
		}
		return node;
	}

	/**
	 * Vraća veličinu stabla, odnosno broj čvorova u stablu.
	 * 
	 * @param node korijen stabla
	 * @return	   broj čvorova u stablu
	 */
	public static int treeSize(TreeNode node) {
		if (node == null) {
			return 0;
		} else {
			return treeSize(node.left) + treeSize(node.right) + 1;
		}
	}

	/**
	 * Provjerava nalazi li se čvor s traženom vrijednosti u stablu.
	 * 
	 * @param node  korijen stabla
	 * @param value tražena vrijednost
	 * @return		<code>true</code> ako se tražena vrijednost nalazi u stablu,
	 * 				<code>false</code> inače
	 */
	public static boolean containsValue(TreeNode node, int value) {
		if (node == null) {
			return false;
		} else if (value < node.value) {
			return containsValue(node.left, value);
		} else if (value > node.value) {
			return containsValue(node.right, value);
		} else {
			return true;
		}
	}

	/**
	 * Ispisuje vrijednosti sadržane u stablu u rastućem poretku.
	 * 
	 * @param node korijen stabla
	 */
	public static void printAscending(TreeNode node) {
		if (node == null) {
			return;
		}
		printAscending(node.left);
		System.out.print(node.value + " ");
		printAscending(node.right);
	}

	/**
	 * Ispisuje vrijednosti sadržane u stablu u padajućem poretku.
	 * 
	 * @param node korijen stabla
	 */
	public static void printDescending(TreeNode node) {
		if (node == null) {
			return;
		}
		printDescending(node.right);
		System.out.print(node.value + " ");
		printDescending(node.left);
	}

}
