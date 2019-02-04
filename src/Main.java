import java.util.*;


import aima.core.search.csp.Assignment;
import aima.core.search.csp.CSP;
import aima.core.search.csp.CSPStateListener;
import aima.core.search.csp.Domain;
import aima.core.search.csp.ImprovedBacktrackingStrategy;
import aima.core.search.csp.NotEqualConstraint;
import aima.core.search.csp.SolutionStrategy;
import aima.core.search.csp.Variable;

public class Main {

	private static CSP setupCSP() {
		CSP csp = null;
//		In five houses, each with a different color, live five persons of different nationality,
//		each of whom prefers a different brand of cigarettes, a different drink, and a different pet.
//		The five houses are arranged in a row (no house has more than 2 neighbors).   
//		# The Englishman lives in the red house.
//		# The Spaniard owns the dog.
//		# Coffee is drunk in the green house.
//		# The Ukrainian drinks tea.
//		# The green house is immediately to the right of the ivory house.
//		# The Old Gold smoker owns snails.
//		# Kools are smoked in the yellow house.
//		# Milk is drunk in the middle house.
//		# The Norwegian lives in the first house.
//		# The man who smokes Chesterfields lives in the house next to the man with the fox.
//		# Kools are smoked in the house next to the house where the horse is kept.
//		# The Lucky Strike smoker drinks orange juice.
//		# The Japanese smokes Parliaments.
//		# The Norwegian lives next to the blue house.
//
//		Now, who drinks water? Who owns the zebra?
				
		String[] colors = {"Red", "Green", "Ivory", "Yellow", "Blue"};
		String[] nations = {"Englishman", "Spaniard", "Norwegian", "Ukrainian", "Japanese"};
		String[] cigarettes = {"Old Gold", "Kools", "Chesterfields", "Lucky Strike", "Parliaments"};
		String[] drinks = {"Water", "Orange juice", "Tea", "Coffee", "Milk"};
		String[] pets = {"Zebra", "Dog", "Fox", "Snails", "Horse"};

		/*
		// TODO create variables, e.g.,
		Variable var1 = new Variable("Color");
		Variable var2 = new Variable("Nationality");
		Variable var3 = new Variable("Cigaretts");
		Variable var4 = new Variable("Drink");
		Variable var5 = new Variable("Pet");
		Variable var6 = new Variable("House");
		*/

		HashMap<String, Variable> variables = new HashMap<>();

		for(String color : colors) {
			variables.put(color, new Variable(color));
		}

		for ( String nation : nations) {
			variables.put(nation, new Variable(nation));
		}

		for ( String cigarette : cigarettes ) {
			variables.put(cigarette, new Variable(cigarette));
		}

		for ( String drink : drinks ) {
			variables.put(drink, new Variable(drink));
		}

		for ( String pet : pets ) {
			variables.put(pet, new Variable(pet));
		}

		/*
		List<Variable> variables = null;
		// TODO add all your variables to this list, e.g.,
		//Væri betra að gera þetta allt sér lista og leita í þeim ? Map ??
		variables.add(var1);
		variables.add(var2);
		variables.add(var3);
		variables.add(var4);
		variables.add(var5);
		variables.add(var6);
		*/
		
		//csp = new CSP(variables);

		// TODO set domains of variables, e.g.,
		// Domain d1 = new Domain(new String[]{"foo", "bar"});
		// csp.setDomain(var1, d1);
		// Domain d2 = new Domain(new Integer[]{1, 2});
		// csp.setDomain(var2, d2);
		/*
		Domain d1 = new Domain(colors);
		csp.setDomain(var1, d1);
		Domain d2 = new Domain(nations);
		csp.setDomain(var2, d2);
		Domain d3 = new Domain(cigarettes);
		csp.setDomain(var3, d3);
		Domain d4 = new Domain(drink);
		csp.setDomain(var4, d4);
		Domain d5 = new Domain(pet);
		csp.setDomain(var5, d5);
		*/
		for (Variable variable : variables.values()) {
			csp.setDomain(variable, new Domain(new ArrayList<Integer>(Arrays.asList(1,2,3,4,5))));
		}

		
		// TODO add constraints, e.g.,
		// csp.addConstraint(new NotEqualConstraint(var1, var2)); // meaning var1 != var2
		// csp.addConstraint(new EqualConstraint(var1, var2)); // meaning var1 == var2
		// csp.addConstraint(new SuccessorConstraint(var1, var2)); // meaning var1 == var2 + 1
		// csp.addConstraint(new DifferByOneConstraint(var1, var2)); // meaning var1 == var2 + 1 or var1 == var2 - 1


		// Pairwise constraint
		for (String color : colors) {
			for (String color_2 : colors) {
				if (!color.equals(color_2)) {
					csp.addConstraint(new NotEqualConstraint(variables.get(color), variables.get(color_2)));
				}
			}
		}

		for (String nation : nations) {
			for (String nation_2 : nations) {
				if (!nation.equals(nation_2)) {
					csp.addConstraint(new NotEqualConstraint(variables.get(nation), variables.get(nation_2)));
				}
			}
		}

		for (String cigarette : cigarettes) {
			for (String cigarette_2 : cigarettes) {
				if (!cigarette.equals(cigarette_2)) {
					csp.addConstraint(new NotEqualConstraint(variables.get(cigarette), variables.get(cigarette_2)));
				}
			}
		}

		for (String drink : drinks) {
			for (String drink_2 : drinks) {
				if (!drink.equals(drink_2)) {
					csp.addConstraint(new NotEqualConstraint(variables.get(drink), variables.get(drink_2)));
				}
			}
		}

		for (String pet : pets) {
			for (String pet_2 : pets) {
				if (!pet.equals(pet_2)) {
					csp.addConstraint(new NotEqualConstraint(variables.get(pet), variables.get(pet_2)));
				}
			}
		}

		// Constraints from description
		csp.addConstraint(new EqualConstraint(variables.get("Englishman"), variables.get("Red")));
		csp.addConstraint(new EqualConstraint(variables.get("Spaniard"), variables.get("Dog")));
		csp.addConstraint(new EqualConstraint(variables.get("Coffee"), variables.get("Green")));
		csp.addConstraint(new EqualConstraint(variables.get("Ukranian"), variables.get("Tea")));
		csp.addConstraint(new SuccessorConstraint(variables.get("Green"), variables.get("Ivory")));
		csp.addConstraint(new EqualConstraint(variables.get("Old Gold"), variables.get("Snails")));
		csp.addConstraint(new EqualConstraint(variables.get("Kools"), variables.get("Yellow")));

		// TODO: finna leið fyrir milk is drunk in the middle house
		// TODO: finna leið fyrir norwegian lives in the first house

		csp.addConstraint(new DifferByOneConstraint(variables.get("Chesterfields"), variables.get("Fox")));
		csp.addConstraint(new DifferByOneConstraint(variables.get("Kools"), variables.get("Horse")));
		csp.addConstraint(new EqualConstraint(variables.get("Lucky Strike"), variables.get("Orange juice")));
		csp.addConstraint(new EqualConstraint(variables.get("Japanese"), variables.get("Parliments")));
		csp.addConstraint(new DifferByOneConstraint(variables.get("Norwegian"), variables.get("Blue")));

		
		return csp;
	}

	private static void printSolution(Assignment solution) {
		// TODO print out useful answer
		// You can use the following to get the value assigned to a variable:
		// Object value = solution.getAssignment(var); 
		// For debugging it might be useful to print the complete assignment and check whether
		// it makes sense.
		System.out.println("solution:" + solution);
	}
	
	/**
	 * runs the CSP backtracking solver with the given parameters and print out some statistics
	 * @param description
	 * @param enableMRV
	 * @param enableDeg
	 * @param enableAC3
	 * @param enableLCV
	 */
	private static void findSolution(String description, boolean enableMRV, boolean enableDeg, boolean enableAC3, boolean enableLCV) {
		CSP csp = setupCSP();

		System.out.println("======================");
		System.out.println("running " + description);
		
		long startTime, endTime;
		startTime = System.currentTimeMillis();
		SolutionStrategy solver = new ImprovedBacktrackingStrategy(enableMRV, enableDeg, enableAC3, enableLCV);
		final int nbAssignments[] = {0};
		solver.addCSPStateListener(new CSPStateListener() {
			@Override
			public void stateChanged(Assignment arg0, CSP arg1) {
				nbAssignments[0]++;
			}
			@Override
			public void stateChanged(CSP arg0) {}
		});
		Assignment solution = solver.solve(csp);
		endTime = System.currentTimeMillis();
		System.out.println("runtime " + (endTime-startTime)/1000.0 + "s" + ", number of assignments (visited states):" + nbAssignments[0]);
		printSolution(solution);
	}

	/**
	 * main procedure
	 */
	public static void main(String[] args) throws Exception {
		// run solver with different parameters
		findSolution("backtracking + AC3 + most constrained variable + least constraining value", true, true, true, true);
		findSolution("backtracking + AC3 + most constrained variable", true, true, true, false);
		findSolution("backtracking + AC3", false, false, true, false);
		findSolution("backtracking + forward checking + most constrained variable + least constraining value", true, true, false, true);
		findSolution("backtracking + forward checking + most constrained variable", true, true, false, false);
		findSolution("backtracking + forward checking", false, false, false, false);
	}

}
