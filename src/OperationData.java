public class OperationData {
    private int operand1;
    private int operand2;
    private char operator;

    public OperationData(int operand1, int operand2, char operator) {
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operator = operator;
    }

    public int calculate() throws Exception {
        switch (operator) {
            case '+':
                return operand1 + operand2;
            case '-':
                return operand1 - operand2;
            case '*':
                return operand1 * operand2;
            case '/':
                return operand1 / operand2;
            default: 
                throw new Exception("Unknown operator" + operator);
        }
    }
}
