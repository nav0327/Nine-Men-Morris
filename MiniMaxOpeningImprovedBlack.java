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

public class MiniMaxOpeningImprovedBlack {
    private static int staticEstimateCounter=0;
    private static int orgdepth;
    private static String orginput;
    //    b="";
    String resultBoard;
    private static List<String> generateAdd(String inputBoard){
        List<String> l = new ArrayList<>();
        for(int i=0; i<inputBoard.length(); i++){
            if(inputBoard.charAt(i)=='x'){
                StringBuilder b = new StringBuilder(inputBoard);
                b.setCharAt(i,'W');
                if(closeMill(i,b.toString())){
                    generateRemove(b,l);
                }else{
                    l.add(b.toString());
                }
            }
        }
//        System.out.println(l);
        return l;
    }

    public static void main(String[] args) throws IOException {
        String input = args[0] ,output = args[1],board="";
        int depth = Integer.valueOf(args[2]);
        orgdepth = Integer.valueOf(args[2])+1;
        try {
            File file = new File(input);
            BufferedReader br = new BufferedReader(new FileReader(file));
            board = br.readLine();
//                System.out.println("The input board is: "+board);
//            orginput = board;
        } catch (Exception FileNotFound) {
            System.out.println("file not found");
        }
        Pair<String,Integer> estimate = maxMin(depth+1,swapPieces(board));
        System.out.println("\nBoard position: "+swapPieces(estimate.getKey()));
        System.out.println("\nPositions evaluated by static estimation: "+staticEstimateCounter);
        System.out.println("\nMINIMAX estimate: "+estimate.getValue());
        Files.write(Paths.get(output),estimate.getKey().getBytes());
    }

    private static Pair<String,Integer> maxMin(int depth, String board){
//        System.out.println(depth);
        if(depth == 1){
//            System.out.println(board);
//            resultBoard = board;
//            System.out.println(board+" estimate "+staticEstimator(board)+"  depth "+depth+" child "+board+"  maxmin");
            return new Pair<>(board,staticEstimator(board));
        }else{
            int v = Integer.MIN_VALUE;
            String b="";
            List<String> list = new ArrayList<>(generateAdd(board));
//            System.out.println(list+"###########");
            for(String x : list){
//                v = Math.max(v,minMax(depth,x));
                if(depth>0) {
                    Pair<String, Integer> pair = minMax(depth - 1, x);
                    if (v < pair.getValue()) {
                        v = pair.getValue();
//                        if(depth!=orgdepth) {
                        b = pair.getKey();
//                        }
                    }
                }

            }
//            System.out.println(board+" estimate "+v+"  depth "+depth+" child "+b+"  maxmin");
            if(depth==orgdepth) {
                return new Pair<>(b, v);
            }else{
                return new Pair<>(board, v);
            }
//            System.out.println(board);
        }
    }

    private static Pair<String,Integer> minMax(int depth,String board){
//        System.out.println(depth);
        if(depth == 1){
//            System.out.println(board);
//            resultBoard = board;
//            System.out.println(board+"  estimate "+staticEstimator(board)+"  depth "+depth+" child "+board+"  minmax");
            return new Pair<>(board,staticEstimator(board));
        }else {
            int v = Integer.MAX_VALUE;
            String b="";
            List<String> list = new ArrayList<>(generateAdd(board));
//            System.out.println(list+"###########");
            for(String x : list){
//                v = Math.min(v,maxMin(depth,x));
                if(depth>0) {
                    Pair<String, Integer> pair = maxMin(depth - 1, x);
                    if (v > pair.getValue()) {
                        v = pair.getValue();
//                        if(depth==orgdepth) {
                        b = pair.getKey();
//                        }
                    }
                }

            }
//            System.out.println(board);
//            System.out.println(board+"  estimate "+v+"  depth "+depth+" child "+b+"  minmax");
            if(depth==orgdepth){
                return new Pair<>(b,v);
            }else {
                return new Pair<>(board, v);
            }
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
    }

    private static List<Integer> neighbors(int j){
        List<Integer> neighbors = new ArrayList<>();
        switch (j){
            case 0:
                neighbors.addAll(Arrays.asList(1,3,8));
                break;
            case 1:
                neighbors.addAll(Arrays.asList(0,4,2));
                break;
            case 2:
                neighbors.addAll(Arrays.asList(1,5,13));
                break;
            case 3:
                neighbors.addAll(Arrays.asList(0,9,6,4));
                break;
            case 4:
                neighbors.addAll(Arrays.asList(3,1,5));
                break;
            case 5:
                neighbors.addAll(Arrays.asList(4,7,12,2));
                break;
            case 6:
                neighbors.addAll(Arrays.asList(3,10,7));
                break;
            case 7:
                neighbors.addAll(Arrays.asList(6,11,5));
                break;
            case 8:
                neighbors.addAll(Arrays.asList(20,9,0));
                break;
            case 9:
                neighbors.addAll(Arrays.asList(3,8,17,10));
                break;
            case 10:
                neighbors.addAll(Arrays.asList(9,14,6));
                break;
            case 11:
                neighbors.addAll(Arrays.asList(16,12,7));
                break;
            case 12:
                neighbors.addAll(Arrays.asList(11,19,13,5));
                break;
            case 13:
                neighbors.addAll(Arrays.asList(2,12,22));
                break;
            case 14:
                neighbors.addAll(Arrays.asList(17,10,15));
                break;
            case 15:
                neighbors.addAll(Arrays.asList(14,18,16));
                break;
            case 16:
                neighbors.addAll(Arrays.asList(15,19,11));
                break;
            case 17:
                neighbors.addAll(Arrays.asList(20,9,14,18));
                break;
            case 18:
                neighbors.addAll(Arrays.asList(17,21,19,15));
                break;
            case 19:
                neighbors.addAll(Arrays.asList(18,22,12,16));
                break;
            case 20:
                neighbors.addAll(Arrays.asList(8,17,21));
                break;
            case 21:
                neighbors.addAll(Arrays.asList(20,18,22));
                break;
            case 22:
                neighbors.addAll(Arrays.asList(21,19,13));
                break;

        }
        return neighbors;
    }

    private static int staticEstimator(String board){
        int whitePieces=0,blackPieces=0;
        float whitePieceWeight=0;
        for(int i=0; i<board.length(); i++) {
            if (board.charAt(i) == 'W') {
                whitePieces++;
                List<Integer> temp = neighbors(i);
                int valCount=0;
                for(int x : temp){
                    if(board.charAt(x)=='x'){
                        valCount++;
                    }
                }
                switch (valCount){
                    case 4:
                        whitePieceWeight+=0.75;
                        break;
                    case 3:
                        whitePieceWeight+=0.50;
                        break;
                    case 2:
                        whitePieceWeight+=0.25;
                        break;
                    case 1:
                        whitePieceWeight+=0.10;
                        break;
                    case 0:
                        whitePieceWeight+=0;
                        break;
                }

            } else if (board.charAt(i) == 'B') {
                blackPieces++;
            }
        }
        int blackMilltobe = blackMilltobe(board);
        staticEstimateCounter++;
        return ((whitePieces-blackPieces)+Math.round(whitePieceWeight)-blackMilltobe);
    }

    public static int blackMilltobe(String board){
        int blackMilltoBe=0;
        for(int i=0; i<board.length(); i++){
            if(board.charAt(i)=='x'){
                StringBuilder temp = new StringBuilder(board);
                temp.setCharAt(i,'B');
                if(closeMill(i,temp.toString())){
                    blackMilltoBe++;
                }
            }
        }
        return blackMilltoBe;
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


