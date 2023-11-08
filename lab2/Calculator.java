//Group 1 Pavlos Anagnostou 5440 Nikolaos Ntermaris 5477

import java.io.* ;

public class Calculator
{
    private static void Calculate(String expr)
    {
        int N = expr.length();
        char[] a = expr.toCharArray();  // store expression in an character array
        Stack<Character> operators = new Stack<Character>();  // stack to store operators
        Stack<Integer> values = new Stack<Integer>();  // stack to store operators
        Stack<Character> formatting = new Stack<Character>();  // stack to store formatting


        // First and Last character must be a parenthesis
        // if not, then it is an invalid expression
        if(a[0] != '('){
            System.out.println("Invalid expression First character must be '('");
            return;
        }
        if(a[N-1] != ')'){
            System.out.println("Invalid expression Last character must be ')'");
            return;
        }


        for (int i=0; i<N; i++)
        {
            // a[i] is the current character
            // if a[i] is a digit, push it to the stack
            int x = 0;
            if ((a[i] >= '0') && (a[i] <= '9')) {
                x = 0;
                while ((a[i] >='0') && (a[i] <= '9')) {
                    x = 10*x + (a[i++]-'0'); //converts char to int
                }                            // also checks if number is multi-digit
                //the loop runs as long as next char is number
                //it keeps previous digits on x and adds the new one
                values.push(x);
                formatting.push('n');        //whenever a number is pushed in values,
                                             //"n" is pushed in formatting stack
            }
            if(a[i] == '+' || a[i] == '-' || a[i] == '*'){
                operators.push(a[i]); //whenever an operand is pushed in operators,
                formatting.push('o'); //"o" is pushed in formatting stack
            }else if(a[i] == ')'){
                /*this is a check for errors in formatting
                  when c=")" the program finds the previous 4 elements
                  if they follow the format "(", number, operator, number
                  the program procceeds to calculate the result within the parenthesis*/
                try {
                    char f3 = formatting.pop(); // n stands for number
                    char f2 = formatting.pop(); // o for operator
                    char f1 = formatting.pop(); // n
                    char f0 = formatting.pop(); // (
                    if(f3 != 'n' || f2 != 'o' || f1 != 'n' || f0 != '('){
                        // the format is wrong
                        printError(i, a, N);
                        return;
                    }
                }catch(Exception e){
                    // there are not enough elements in formatting stack
                    printError(i, a, N);
                    return;
                }
                int v2 = values.pop(); // pop the last two numbers
                int v1 = values.pop();
                char op = operators.pop(); // pop the last operator
                values.push(calculate(v1, v2, op)); //calculate the operation inside parenthesis
                formatting.push('n');
            }else if(a[i] == '('){
                //whenever a "(" is pushed in formatting,
                formatting.push('(');
            }
        }
        formatting.pop(); //pop the last element of formatting stack
        if(!formatting.isEmpty()){
            //if formatting stack is not empty, there is an error in formatting
            printError(N-1, a, N);
            return;
        }
        System.out.println("Result = " + values.pop()); //print final result
    }

    public static void printError(int pos, char[] a, int N){
        System.out.println("Invalid expression There is something wrong with the expression");
        for(int j = 0; j < N; j++){
            System.out.print(a[j]); //prints the entire input with a pointer at the character in which an error was detected
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
                return v1+v2; //used to calculate the result of an operation inside parenthesis
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
