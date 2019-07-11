import java.util.ArrayList;
import java.util.Scanner;

interface QuestionGenerator {
    public String[] generateQuestion();
}

class PrimesGenerator implements QuestionGenerator {

    private static final boolean FILTER_EVENS = true;
    private int maxRange;
    private ArrayList<Integer> generatedPrimes;

    public PrimesGenerator() {
        this(300);
    }

    public PrimesGenerator(int maxRange) {
        this.maxRange = maxRange;
        this.generatedPrimes = new ArrayList<Integer>();
        generatedPrimes.add(2);
        OUTER: for(int i = 3; i<=maxRange; i+=2) {
            for(int j = 3; j<=Math.sqrt(i); j+=2) {
                if(i % j == 0) continue OUTER;
            }
            generatedPrimes.add(i);
        }
    }

    @Override
    public String[] generateQuestion() {
        int number = 0;
        if(FILTER_EVENS) {
            while(number % 2 == 0)
                number = (int) (Math.random() * (this.maxRange + 1));
        } else number = (int) (Math.random() * (this.maxRange + 1));

        String isPrime = (this.generatedPrimes.contains(number)) ?
            "yes" : "no";
        return new String[] { "Is " + number + " a prime number?", isPrime};
    }
}

class AdditionGenerator implements QuestionGenerator {

    private int max_value;

    public AdditionGenerator() {
        this(3);
    }

    public AdditionGenerator(int num_digits) {
        this.max_value = (int) Math.pow(10, num_digits);
    }

    @Override
    public String[] generateQuestion() {
        int a = (int) (Math.random() * this.max_value);
        int b = (int) (Math.random() * this.max_value);
        return new String[] {a + " + " + b + " =", Integer.toString(a+b)};
    }
}

class SubtractionGenerator implements QuestionGenerator {

    private int max_value;

    public SubtractionGenerator() {
        this(3);
    }

    public SubtractionGenerator(int num_digits) {
        this.max_value = (int) Math.pow(10, num_digits);
    }

    @Override
    public String[] generateQuestion() {
        int a = (int) (Math.random() * this.max_value);
        int b = (int) (Math.random() * this.max_value);
        return new String[] {Math.max(a, b) + " - " + Math.min(a, b) + " =",
                Integer.toString(Math.abs(a-b))};
    }
}

class MultiplicationGenerator implements QuestionGenerator {

    private int max_value;

    public MultiplicationGenerator() {
        this(2);
    }

    public MultiplicationGenerator(int num_digits) {
        this.max_value = (int) Math.pow(10, num_digits);
    }

    @Override
    public String[] generateQuestion() {
        int a = (int) (Math.random() * this.max_value);
        int b = (int) (Math.random() * this.max_value);
        return new String[] {a + " * " + b + " =", Integer.toString(a*b)};
    }
}

class DivisionGenerator implements QuestionGenerator {

    private int max_ans_value;
    private int max_divisor_value;

    public DivisionGenerator() {
        this(3, 1);
    }

    public DivisionGenerator(int max_dividend, int max_divisor) {
        this.max_ans_value = (int) Math.pow(10, max_dividend - max_divisor) - 1;
        this.max_divisor_value = (int) Math.pow(10, max_divisor) - 1;
    }

    @Override
    public String[] generateQuestion() {
        int ans = (int) (Math.random() * this.max_ans_value) + 1;
        int divisor = (int) (Math.random() * this.max_divisor_value) + 1;
        return new String[] {ans * divisor + " / " + divisor + " =", Integer.toString(ans)};
    }
}

class SquareRootGenerator implements QuestionGenerator {

    private int max_value = 100;

    @Override
    public String[] generateQuestion() {
        int ans = (int) (Math.random() * this.max_value);
        int squared = (int) Math.pow(ans, 2);
        return new String[] {"²√" + squared + " =", Integer.toString(ans)};
    }
}

class CubeRootGenerator implements QuestionGenerator {

    private int max_value = 100;

    @Override
    public String[] generateQuestion() {
        int ans = (int) (Math.random() * this.max_value);
        int cubed = (int) Math.pow(ans, 3);
        return new String[] {"³√" + cubed + " =", Integer.toString(ans)};
    }
}

abstract class CPBase implements QuestionGenerator {

    protected int max_n;

    public CPBase(int max_n) {
        this.max_n = max_n;
    }

    protected int factorial(int x) {
        if(x == 0) return 1;
        else return x*factorial(x-1);
    }

    protected int permute(int n, int r, boolean doCombination) {
        int result = factorial(n) / factorial(n-r);
        if(doCombination) result /= factorial(r);
        return result;
    }

    protected int[] getRandomNR() {
        int[] result = {0, 0};
        result[0] = (int) (Math.random()*max_n)+1;
        result[1] = (int) (Math.random()*result[0])+1;
        return result;
    }
}

class PermutationGenerator extends CPBase {
    public PermutationGenerator(int max_n) {
        super(max_n);
    }

    public PermutationGenerator() {
        this(6);
    }

    @Override
    public String[] generateQuestion() {
        int[] nr = this.getRandomNR();
        return new String[] {nr[0] + " P " + nr[1] + " =", Integer.toString(this.permute(nr[0], nr[1], false))};
    }
}

class CombinationGenerator extends CPBase {
    public CombinationGenerator(int max_n) {
        super(max_n);
    }

    public CombinationGenerator() {
        this(6);
    }

    @Override
    public String[] generateQuestion() {
        int[] nr = this.getRandomNR();
        return new String[] {nr[0] + " C " + nr[1] + " =", Integer.toString(this.permute(nr[0], nr[1], true))};
    }
}

class TrigGenerator implements QuestionGenerator {
    private String[][] TRIG_TABLE = {
        {"0°", "30°", "45°", "60°", "90°", "120°", "135°", "150°", "180°", "210°",
            "225°", "240°", "270°", "300°", "315°", "330°", "360°"},
        {"0", "π/6", "π/4", "π/3", "π/2", "2π/3", "3π/4", "5π/6", "π", "7π/6",
            "5π/4", "4π/3", "3π/2", "5π/3", "7π/4", "11π/6", "2π"},
        {"sin", "0", "1/2", "sqrt(2)/2", "sqrt(3)/2", "1", "sqrt(3)/2", "sqrt(2)/2", "1/2", "0", "-1/2",
            "-sqrt(2)/2", "-sqrt(3)/2", "-1", "-sqrt(3)/2", "-sqrt(2)/2", "-1/2", "0"},
        {"cos", "1", "sqrt(3)/2", "sqrt(2)/2", "1/2", "0", "-1/2", "-sqrt(2)/2", "-sqrt(3)/2", "-1", "-sqrt(3)/2",
            "-sqrt(2)/2", "-1/2", "0", "1/2", "sqrt(2)/2", "sqrt(3)/2", "1"},
        {"tan", "0", "sqrt(3)/3", "1", "sqrt(3)", "undef.", "-sqrt(3)", "-1", "-sqrt(3)/3", "0", "sqrt(3)/3",
            "1", "sqrt(3)", "undef.", "-sqrt(3)", "-1", "-sqrt(3)/3", "0"},
        {"cot", "undef.", "sqrt(3)", "1", "sqrt(3)/3", "0", "-sqrt(3)/3", "-1", "-sqrt(3)", "undef.", "sqrt(3)",
            "1", "sqrt(3)/3", "0", "-sqrt(3)/3", "-1", "-sqrt(3)", "undef."},
        {"sec", "1", "2sqrt(3)/3", "sqrt(2)", "2", "undef.", "-2", "-sqrt(2)", "-2sqrt(3)/3", "-1", "-2sqrt(3)/3",
            "-sqrt(2)", "-2", "undef.", "2", "sqrt(2)", "2sqrt(3)/3", "1"},
        {"csc", "undef.", "2", "sqrt(2)", "2sqrt(3)/3", "1", "2sqrt(3)/3", "sqrt(2)", "2", "undef.", "-2",
            "-sqrt(2)", "-2sqrt(3)/3", "-1", "-2sqrt(3)/3", "-sqrt(2)", "-2", "undef."}
    };
    private boolean cofunctions;

    public TrigGenerator() {
        this(false);
    }

    public TrigGenerator(boolean cofunctions) {
        this.cofunctions = cofunctions;
    }

    @Override
    public String[] generateQuestion() {
        int randfunction;
        if(this.cofunctions) randfunction = (int) (Math.random() * 6) + 2;
        else randfunction = (int) (Math.random() * 3) + 2;
        int randunit = (Math.random() > 0.5) ? 1 : 0;
        int randval = (int) (Math.random() * 17);
        return new String[] {TRIG_TABLE[randfunction][0] + "(" + TRIG_TABLE[randunit][randval] + ") =", TRIG_TABLE[randfunction][randval+1]};
    }
}

class RoteGenerator implements QuestionGenerator {
    String[][] questions = {
        {"6³ =", "216"},
        {"7³ =", "343"},
        {"8³ =", "512"},
        {"9³ =", "729"},
        {"11³ =", "1331"},
        {"12³ =", "1728"},
        {"13³ =", "2197"},
        {"14³ =", "2744"},
        {"15³ =", "3375"},
        {"What are the first 6 triangle numbers?", "1,3,6,10,15,21"},
        {"1/7 ≈", "0.142857"},
        {"7! =", "5040"},
        {"8! =", "40320"},
        {"9! =", "362880"},
        {"√2 ≈", "1.414"},
        {"√3 ≈", "1.732"},
        {"√5 ≈", "2.236"},
        {"log(2) ≈", "0.301"}
    };
    @Override
    public String[] generateQuestion() {
        return questions[(int) (Math.random() * questions.length)];
    }
}

public class MathTrainer {
    static ArrayList<QuestionGenerator> problemGenerators = new ArrayList<>();
    static ArrayList<String[]> problems = new ArrayList<>();

    public static void main(String[] args) {

        if(args.length != 2) {
            System.out.println("MathTrainer, (c) 2019 Brandon Gong.");
            System.out.println("Usage:");
            System.out.println("    java MathTrainer <question-types> <num-questions>");
            System.out.println("For more information on available question types reference the README.");
            return;
        }

        System.out.print("MathTrainer instance created with ");
        for(int i = 0; i < args[0].length(); i++) {
            switch(args[0].charAt(i)) {
                case 'i': problemGenerators.add(new PrimesGenerator());
                          System.out.print("primes");
                          break;
                case 'a': problemGenerators.add(new AdditionGenerator());
                          System.out.print("addition");
                          break;
                case 's': problemGenerators.add(new SubtractionGenerator());
                          System.out.print("subtraction");
                          break;
                case 'm': problemGenerators.add(new MultiplicationGenerator());
                          System.out.print("multiplication");
                          break;
                case 'd': problemGenerators.add(new DivisionGenerator());
                          System.out.print("division");
                          break;
                case 'q': problemGenerators.add(new SquareRootGenerator());
                          System.out.print("sqrt");
                          break;
                case 'b': problemGenerators.add(new CubeRootGenerator());
                          System.out.print("cbrt");
                          break;
                case 'p': problemGenerators.add(new PermutationGenerator());
                          System.out.print("permutation");
                          break;
                case 'c': problemGenerators.add(new CombinationGenerator());
                          System.out.print("combination");
                          break;
                case 't': problemGenerators.add(new TrigGenerator());
                          System.out.print("trig");
                          break;
                case 'r': problemGenerators.add(new RoteGenerator());
                          System.out.print("rote");
                          break;
            }
            if(i == args[0].length() - 2) System.out.print(", and ");
            else if(i == args[0].length() - 1) break;
            else System.out.print(", ");
        }
        System.out.println(" generators.");
        System.out.println("You will be given questions in sets of " + args[1] + ".");
        Scanner in = new Scanner(System.in);
        int questionsPerSprint = Integer.valueOf(args[1]);
        while(true) {
            System.out.print("\nContinue? (y/n) ");
            String s = in.nextLine();
            if(!(s.equalsIgnoreCase("y"))) break;
            long[] times = new long[questionsPerSprint];
            problems = new ArrayList<>();
            long offset = System.currentTimeMillis();
            for(int i = 0; i < questionsPerSprint; i++) {
                String[] question = problemGenerators.get((int) (Math.random() * problemGenerators.size())).generateQuestion();
                problems.add(question);
                System.out.print(question[0] + " ");
                while(true) {
                    String input = in.nextLine();
                    if(input.equalsIgnoreCase(question[1])) {
                        times[i] = System.currentTimeMillis() - offset;
                        offset = System.currentTimeMillis();
                        break;
                    } else {
                        System.out.println("Incorrect.");
                        System.out.print(question[0] + " ");
                    }
                }
            }
            System.out.println("\n\n=============== END OF SET ===============");
            System.out.println("Summary: ");
            for(int i = 0; i < questionsPerSprint; i++) {
                System.out.println("Question: " + problems.get(i)[0]);
                System.out.println("Correct Answer: " + problems.get(i)[1]);
                System.out.println("Time spent: " + times[i] / 1000d + "s");
                System.out.println();
            }
            long totaltime = 0;
            for(int i = 0; i < times.length; i++) {
                totaltime += times[i];
            }
            totaltime /= times.length;
            System.out.println("\nAverage time per question: " + totaltime / 1000d + "s");
        }
    }
}

