import java.io.* ;

public class Calculator
{   
    private static void Calculate(String expr) 
    {
        int N = expr.length();
        char[] a = expr.toCharArray();  // store expression in an character array
                                        //
        Stack<Character> operators = new Stack<Character>();  // stack to store operators
        Stack<Integer> values = new Stack<Integer>();  // stack to store operators
        Stack<Character> formatting = new Stack<Character>();  // stack to store formatting
                                                       
        int paren = 0;
        // First and Last character must be a digit
        // if not, then it is an invalid expression
        if(a[0] != '('){
            System.out.println("Invalid expression First character must be '('");
            return;
        }
        
	// Pavlitos Comment
        if(a[N-1] != ')'){
            System.out.println("Invalid expression Last character must be ')'");
            return;
        }

        for (int i=0; i<N; i++)
        {
            Character c = a[i];
            // c is the current character
            // if c is a digit, push it to the stack
            int x = 0;
            if ((a[i] >= '0') && (a[i] <= '9')) {
                x = 0;
                while ((a[i] >='0') && (a[i] <= '9')) {
                    x = 10*x + (a[i++]-'0');
                }
                values.push(x);
                formatting.push('n');
            }
            if(a[i] == '+' || a[i] == '-' || a[i] == '*'){
                operators.push(a[i]);
                formatting.push('o');
            }else if(a[i] == ')'){
                try {
                    char f3 = formatting.pop(); // n
                    char f2 = formatting.pop(); // o
                    char f1 = formatting.pop(); // n
                    char f0 = formatting.pop(); // (
                    if(f3 != 'n' || f2 != 'o' || f1 != 'n' || f0 != '('){
                        printError(i, a, N);
                        return;
                    }
                }catch(Exception e){
                    printError(i, a, N);
                    return;
                }
                int v2 = values.pop();
                int v1 = values.pop();
                char op = operators.pop();
                values.push(calculate(v1, v2, op));
                formatting.push('n');
            }else if(a[i] == '('){
                formatting.push('(');
            }
        }
        System.out.println("Result = " + values.pop());
    }

    public static void printError(int pos, char[] a, int N){
        System.out.println("Invalid expression There is something wrong with the expression");
        for(int j = 0; j < N; j++){
            System.out.print(a[j]);
        }
        System.out.println();
        for(int j = 0; j<N; j++){
            if(pos != j){
                System.out.print(" ");
            }else{
                System.out.print("^");
            }
        }
        System.out.println();
    }

    public static int calculate(int v1, int v2, Character op){
        switch(op){
            case '+':
                return v1+v2;
            case '-':
                return v1-v2;
            case '*':
                return v1*v2;
            default:
                return 0;
        }
    }
    
    public static void main(String[] args)
    {
        String expr = args[0];
        System.out.println("Input expression = " + expr + " , length = " + expr.length());
                
        Calculate(expr);
    }
}
