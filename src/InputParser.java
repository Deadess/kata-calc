public class InputParser {
    private String input;
    private int pos;
    private NumType parseType;

    private char[] romanNumsChars = {'V', 'I', 'X'};
    private char[] arabicNumsChars = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
    private char[] operatorChars = {'+', '-', '*', '/'};

    public InputParser(String input) {
        this.input = input;
    }

    OperationData parseInput() throws Exception {
        //start parse
        int a = 0, b = 0;
        char op = ' ';
        boolean formatCorrect = true;

        for (pos = 0; pos < input.length(); pos++) {
            if(input.charAt(pos) == ' ') {
                //skip
                pos++;
            }
            else {
                for(var item : romanNumsChars) {
                    if(item == input.charAt(pos)) {
                        parseType = NumType.ROMAN;
                        break;   
                    }
                }
                for(var item : arabicNumsChars) {
                    if(item == input.charAt(pos)) {
                        parseType = NumType.ARABIC;
                        break;
                    }
                }
                
                a = parseNum(parseType);
                op = parseOperator();
                b = parseNum(parseType);

                if(pos < input.length()) {
                    int i_pos = pos;
                    try {
                        parseOperator();
                        formatCorrect = false;
                    }
                    catch(Exception e) {
                        pos = i_pos;
                    }

                    try {
                        parseNum(parseType);
                        formatCorrect = false;
                    }
                    catch(Exception e) {
                        pos = i_pos;
                    }

                    if(!formatCorrect) {
                        throw new Exception("Incorrect format. Expected 2 operands and 1 operator, e.x. [a + b]");
                    }
                }
                
            }
        }
        return new OperationData(a, b, op);
    }

    int parseNum(NumType type) throws Exception {
        int num = 0;

        switch(type) {
            case ROMAN:
                num = parseRomanNum(); 
                break;
            case ARABIC:
                num = parseArabicNum(); 
                break;
        }

        if (num > 0 && num <=10) {
            return num;
        }
        else {
            throw new Exception("Number value is out of expected boundary. Expected 1 ... 10");
        } 
    }

    int parseRomanNum() throws Exception {
        int number = 0;
        boolean supposedNumFound = false;
        while(pos < input.length()){
            char c = input.charAt(pos++);
            if(c == ' ' && !supposedNumFound) {
                continue;
            }
            else if (c != ' ') {
                supposedNumFound = true;
            }
            if(supposedNumFound) {
                number += romanCharToInt(c);

                if(pos < input.length()) {
                    try {
                        int numberNext = romanCharToInt(input.charAt(pos));
                        if(numberNext > number) {
                            number = (numberNext - number);
                            pos++;
                        }
                    }
                    catch (Exception e) {
                        break;
                    }   
                }
                else break;
            } 
        }
        return number;
    }

    int parseArabicNum() throws Exception {
        int i_pos = pos;
        boolean supposedNumFound = false;

        while(pos < input.length()) {
            char c = input.charAt(pos);
            if(!supposedNumFound) {
                if (c == ' ') {
                    pos++;
                }
                else {
                    supposedNumFound = true;
                    i_pos = pos;
                }
            } 

            if (supposedNumFound && c >= 48 && c <= 57)
            {
                pos++;
            }
            else if (supposedNumFound) break;
        }
        return Integer.parseInt(input.substring(i_pos, pos),  10);
    }

    int romanCharToInt(char c) throws Exception {
        switch(c) {
            case 'I': 
                return 1;
            case 'V': 
                return 5;
            case 'X': 
                return 10;
            default: 
                throw new Exception("Roman num not recognized");
        }
    }

    char parseOperator() throws Exception {
        while (pos < input.length()) {
            if(input.charAt(pos) == ' ') {
                pos++;
            }
            else {
                for(var item : operatorChars) {
                    //debug
                    char c =  input.charAt(pos);

                    if(item == c) {
                        pos++;
                        return c;
                    }
                }
            }
        }
        
        throw new Exception("Operator missing");
    }

    enum NumType {
        ROMAN,
        ARABIC
    }
}
