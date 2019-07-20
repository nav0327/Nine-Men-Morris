import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.exit;
import static java.lang.System.setOut;
public class MiniMaxGameImprovedBlack {
    private static int staticEstimateCounter=0;
    private static int orgdepth;
    private static int genratemove=0;
    private static List<String> GenerateHopping(String b){
        List<String> l = new ArrayList<>();
        StringBuilder board = new StringBuilder(b);
        for(int i=0; i<board.length(); i++){
            if(board.charAt(i)=='W'){
                for(int j=0; j<board.length() ; j++){
                    if(board.charAt(j)=='x'){
                        StringBuilder temp = new StringBuilder(board);
                        temp.setCharAt(i,'x');
                        temp.setCharAt(j,'W');
                        if(closeMill(j,temp.toString())){
                            generateRemove(temp,l);
                        }else{
                            l.add(temp.toString());
                        }
                    }
                }
            }
        }
        return l;

    }

    private static List<String> GenerateMove(String board){
        genratemove++;
        List<String> l = new ArrayList<>();
        for(int i=0; i<board.length(); i++){
            if(board.charAt(i)=='W'){
                List<Integer> n = neighbors(i);
                for(int x: n){
                    if(board.charAt(x)=='x'){
                        StringBuilder temp = new StringBuilder(board);
                        temp.setCharAt(i,'x');
                        temp.setCharAt(x,'W');
                        if(closeMill(x,temp.toString())){
                            generateRemove(temp,l);
                        }else{
                            l.add(temp.toString());
                        }
                    }
                }
            }
        }
//        System.out.println(l.size());
        return l;
    }

    public static void main(String[] args) throws IOException {
        String input = args[0],output = args[1],board="";
        int depth = Integer.valueOf(args[2]);
        orgdepth = Integer.valueOf(args[2])+1;
        try {
            File file = new File(input);
            BufferedReader br = new BufferedReader(new FileReader(file));
            board = br.readLine();
//                System.out.println("MiniMaxGame\n");
//                System.out.println("The input board is: "+board);
        } catch (Exception FileNotFound) {
            System.out.println("file not found");
        }
        List<String> l = new ArrayList<>();
        l.add(board);
        Pair<String,Integer> estimate = maxMin(depth+1,swapPieces(board));
        System.out.println("\nBoard position: "+swapPieces(estimate.getKey()));
        System.out.println("\nPositions evaluated by static estimation: "+staticEstimateCounter);
        System.out.println("\nMINIMAX estimate: "+estimate.getValue());
        Files.write(Paths.get(output),estimate.getKey().getBytes());
    }

    private static Pair<String,Integer> maxMin(int depth, String board){
//        System.out.println("maxmin");
        if(depth == 1){
//            resultBoard = board;
            return new Pair<>(board,staticEstimator(board));
        }else{
            int v = Integer.MIN_VALUE;
            String b="";
            List<String> list = decideHopOrMove(board);
            for(String x : list) {
//                v = Math.max(v,minMax(depth,x));
                if (depth > 0) {
                    Pair<String, Integer> pair = minMax(depth-1, x);
                    if (v < pair.getValue()) {
                        v = pair.getValue();
                        b = pair.getKey();
                    }
                }
            }
            if(depth==orgdepth) {
                return new Pair<>(b, v);
            }else{
                return new Pair<>(board, v);
            }
        }
    }

    private static Pair<String,Integer> minMax(int depth, String board){
//        System.out.println("minmax");
        if(depth == 1){
//            resultBoard = board;
            return new Pair<>(board,staticEstimator(board));
        }else{
            int v = Integer.MAX_VALUE;
            String b="";
            List<String> list = decideHopOrMove(board);
            for(String x : list) {
//                v = Math.min(v,maxMin(depth,x));
                if (depth > 0) {
                    Pair<String, Integer> pair = maxMin(depth-1, x);
                    if (v > pair.getValue()) {
                        v = pair.getValue();
                        b = pair.getKey();
                    }
                }
            }
            if(depth==orgdepth){
                return new Pair<>(b,v);
            }else {
                return new Pair<>(board, v);
            }
        }
    }

    private static List<String> decideHopOrMove(String board){
        int noOfWhite=0;
        for(int i=0; i<board.length(); i++){
            if(board.charAt(i)=='W'){
                noOfWhite++;
            }
        }
        if(noOfWhite==3){
            return new ArrayList<>(GenerateHopping(board));
        }else{
            return new ArrayList<>(GenerateMove(board));
        }
    }

    private static boolean closeMill(int j, String b){
        char C = b.charAt(j);
        if(C == 'x'){
            System.out.println("ERROR in close Mill");
            exit(0);
        }
        switch (j){
            case 0:
                if((b.charAt(1)== C && b.charAt(2)== C)||(b.charAt(3)== C && b.charAt(6)== C)||(b.charAt(8)== C && b.charAt(20)== C)){
                    return true;
                }else{
                    return false;
                }
            case 1:
                if((b.charAt(0)== C && b.charAt(2)== C)){
                    return true;
                }else{
                    return false;
                }
            case 2:
                if((b.charAt(0)== C && b.charAt(1)== C)||(b.charAt(13)== C && b.charAt(22)== C)||(b.charAt(5)== C && b.charAt(7)== C)){
                    return true;
                }else{
                    return false;
                }
            case 3:
                if((b.charAt(9)== C && b.charAt(17)== C)||(b.charAt(4)== C && b.charAt(5)== C)||(b.charAt(0)== C && b.charAt(6)== C)){
                    return true;
                }else{
                    return false;
                }
            case 4:
                if((b.charAt(3)== C && b.charAt(5)== C)){
                    return true;
                }else{
                    return false;
                }
            case 5:
                if((b.charAt(3)== C && b.charAt(4)== C)||(b.charAt(12)== C && b.charAt(19)== C)||(b.charAt(2)== C && b.charAt(7)== C)){
                    return true;
                }else{
                    return false;
                }
            case 6:
                if((b.charAt(0)== C && b.charAt(3)== C)||(b.charAt(10)== C && b.charAt(14)== C)){
                    return true;
                }else {
                    return false;
                }
            case 7:
                if((b.charAt(2)== C && b.charAt(5)== C)||(b.charAt(11)== C && b.charAt(16)== C)){
                    return true;
                }else {
                    return false;
                }
            case 8:
                if((b.charAt(0)== C && b.charAt(20)== C)||(b.charAt(9)== C && b.charAt(10)== C)){
                    return true;
                }else {
                    return false;
                }
            case 9:
                if((b.charAt(8)== C && b.charAt(10)== C)||(b.charAt(3)== C && b.charAt(17)== C)){
                    return true;
                }else {
                    return false;
                }
            case 10:
                if((b.charAt(8)== C && b.charAt(9)== C)||(b.charAt(6)== C && b.charAt(14)== C)){
                    return true;
                }else {
                    return false;
                }
            case 11:
                if((b.charAt(12)== C && b.charAt(13)== C)||(b.charAt(7)== C && b.charAt(16)== C)){
                    return true;
                }else {
                    return false;
                }
            case 12:
                if((b.charAt(11)== C && b.charAt(13)== C)||(b.charAt(5)== C && b.charAt(19)== C)){
                    return true;
                }else{
                    return false;
                }
            case 13:
                if((b.charAt(11)== C && b.charAt(12)== C)||(b.charAt(2)== C && b.charAt(22)== C)){
                    return true;
                }else {
                    return false;
                }
            case 14:
                if((b.charAt(17)== C && b.charAt(20)== C)||(b.charAt(15)== C && b.charAt(16)== C)||(b.charAt(6)== C && b.charAt(10)== C)){
                    return true;
                }else {
                    return false;
                }
            case 15:
                if((b.charAt(18)== C && b.charAt(21)== C)||(b.charAt(14)== C && b.charAt(16)== C)){
                    return true;
                }else{
                    return false;
                }
            case 16:
                if((b.charAt(14)== C && b.charAt(15)== C)||(b.charAt(7)== C && b.charAt(11)== C)||(b.charAt(19)== C && b.charAt(22)== C)){
                    return true;
                }else{
                    return false;
                }
            case 17:
                if((b.charAt(14)== C && b.charAt(20)== C)||(b.charAt(3)== C && b.charAt(9)== C)||(b.charAt(18)== C && b.charAt(19)== C)){
                    return true;
                }else{
                    return false;
                }
            case 18:
                if((b.charAt(15)== C && b.charAt(21)== C)||(b.charAt(17)== C && b.charAt(19)== C)){
                    return true;
                }else{
                    return false;
                }
            case 19:
                if((b.charAt(16)== C && b.charAt(22)== C)||(b.charAt(17)== C && b.charAt(18)== C)||(b.charAt(5)== C && b.charAt(12)== C)){
                    return true;
                }else{
                    return false;
                }
            case 20:
                if((b.charAt(0)== C && b.charAt(8)== C)||(b.charAt(14)== C && b.charAt(17)== C)||(b.charAt(21)== C && b.charAt(22)== C)){
                    return true;
                }else {
                    return false;
                }
            case 21:
                if((b.charAt(20)== C && b.charAt(22)== C)||(b.charAt(15)== C && b.charAt(18)== C)){
                    return true;
                }else {
                    return false;
                }
            case 22:
                if((b.charAt(16)== C && b.charAt(19)== C)||(b.charAt(2)== C && b.charAt(13)== C)||(b.charAt(20)== C && b.charAt(21)== C)){
                    return true;
                }else {
                    return false;
                }
        }
        return false;
    }

    private static void generateRemove(StringBuilder b,List<String> l){
        boolean check=false;
        for(int j=0; j<b.length(); j++){
            if(b.charAt(j)=='B'){
                if(!closeMill(j,b.toString())){
                    check = true;
                    StringBuilder temp = new StringBuilder(b);
//                    temp = b;
                    temp.setCharAt(j,'x');
                    l.add(temp.toString());
                }
            }
        }
        if(!check){
            StringBuilder temp = new StringBuilder(b);
            l.add(temp.toString());
        }
//        System.out.println(l.size());
    }


    private static int staticEstimator(String board){
        int whitePieces=0,blackPieces=0;
        for(int i=0; i<board.length(); i++) {
            if (board.charAt(i) == 'W') {
                whitePieces++;
            } else if (board.charAt(i) == 'B') {
                blackPieces++;
            }
        }
        String blackBoard = swapPieces(board);
        List<String> list = decideHopOrMove(blackBoard);
        int blackMilltobe=0;
        blackMilltobe = blackMilltobe(board);
//            System.out.println(blackMilltobe);
        staticEstimateCounter++;
        if(blackPieces<=2){
            return 10000;
        }else if(whitePieces<=2){
            return -10000;
        }else if(list.size()==0){
            return 10000;
        }else{
//                System.out.println(whitePieces-blackPieces+" "+list.size()+" "+blackMilltobe);
            return (1000*(whitePieces-blackPieces-blackMilltobe)-list.size()-(10*blackMilltobe));
        }
    }

    public static int blackMilltobe(String board){
        int blackMilltoBe=0;
        for(int i=0; i<board.length(); i++){
            if(board.charAt(i)=='x'){
                StringBuilder temp = new StringBuilder(board);
                List<Integer> n = neighbors(i);
                for(int x: n){
                    StringBuilder tempp2 = new StringBuilder(temp);
                    tempp2.setCharAt(i,'B');
                    tempp2.setCharAt(x,'x');
                    if(closeMill(i,tempp2.toString())) {
                        blackMilltoBe++;
                        break;
                    }
                }
            }
        }
        return blackMilltoBe;
    }

//    private static List<Integer> neighbors(int location) {
//        switch (location) {
//            case 0:
//                return Arrays.asList(1, 3, 8);
//            case 1:
//                return Arrays.asList(0, 4, 2);
//            case 2:
//                return Arrays.asList(1, 5, 13);
//            case 3:
//                return Arrays.asList(0, 4, 6, 9);
//            case 4:
//                return Arrays.asList(1, 3, 5);
//            case 5:
//                return Arrays.asList(2, 4, 7, 12);
//            case 6:
//                return Arrays.asList(3, 7, 10);
//            case 7:
//                return Arrays.asList(5, 6, 11);
//            case 8:
//                return Arrays.asList(0, 9, 20);
//            case 9:
//                return Arrays.asList(3, 8, 10, 17);
//            case 10:
//                return Arrays.asList(6, 9, 14);
//            case 11:
//                return Arrays.asList(7, 12, 16);
//            case 12:
//                return Arrays.asList(5, 11, 13, 19);
//            case 13:
//                return Arrays.asList(2, 12, 22);
//            case 14:
//                return Arrays.asList(10, 15, 17);
//            case 15:
//                return Arrays.asList(14, 16, 18);
//            case 16:
//                return Arrays.asList(11, 15, 19);
//            case 17:
//                return Arrays.asList(9, 14, 18, 20);
//            case 18:
//                return Arrays.asList(15, 17, 19, 21);
//            case 19:
//                return Arrays.asList(12, 16, 18, 22);
//            case 20:
//                return Arrays.asList(8, 17, 21);
//            case 21:
//                return Arrays.asList(18, 20, 22);
//            case 22:
//                return Arrays.asList(13, 19, 21);
//                default: return new ArrayList<>();
//        }
//    }

    private static List<Integer> neighbors(int location) {
        switch (location) {
            case 0:
                return Arrays.asList(1, 3, 8);
            case 1:
                return Arrays.asList(0, 2, 4);
            case 2:
                return Arrays.asList(1, 5, 13);
            case 3:
                return Arrays.asList(0, 4, 6, 9);
            case 4:
                return Arrays.asList(1, 3, 5);
            case 5:
                return Arrays.asList(2, 4, 7, 12);
            case 6:
                return Arrays.asList(3, 7, 10);
            case 7:
                return Arrays.asList(5, 6, 11);
            case 8:
                return Arrays.asList(0, 9, 20);
            case 9:
                return Arrays.asList(3, 8, 10, 17);
            case 10:
                return Arrays.asList(6, 9, 14);
            case 11:
                return Arrays.asList(7, 12, 16);
            case 12:
                return Arrays.asList(5, 11, 13, 19);
            case 13:
                return Arrays.asList(2, 12, 22);
            case 14:
                return Arrays.asList(10, 15, 17);
            case 15:
                return Arrays.asList(14, 16, 18);
            case 16:
                return Arrays.asList(11, 15, 19);
            case 17:
                return Arrays.asList(9, 14, 18, 20);
            case 18:
                return Arrays.asList(15, 17, 19, 21);
            case 19:
                return Arrays.asList(12, 16, 18, 22);
            case 20:
                return Arrays.asList(8, 17, 21);
            case 21:
                return Arrays.asList(18, 20, 22);
            case 22:
                return Arrays.asList(13, 19, 21);
            default: return new ArrayList<>();
        }
    }


    private static String swapPieces(String b){
        StringBuilder board = new StringBuilder(b);
        for(int i=0; i<board.length(); i++){
            if(board.charAt(i)=='W'){
                board.setCharAt(i,'B');
            }else if(board.charAt(i)=='B'){
                board.setCharAt(i,'W');
            }
        }
        return board.toString();
    }
}


