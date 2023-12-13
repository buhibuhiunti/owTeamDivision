import TeamData.Member;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LoadDatasheet {

    static List<TeamData.Member> loadDatasheet() {
        String tableContents;
        List<TeamData.Member> MemberList = new ArrayList<TeamData.Member>();
        File f;
        BufferedReader br = new BufferedReader(new StringReader(""));
        while(true) {
            try {
                f = new File("memberList.csv");
                br = new BufferedReader((new FileReader(f)));
            }
            catch (IOException e){
                System.out.println(e + "errorCode 000\nファイルが見つかりませんでした\nファイルを配置したらyを入力してください\n" +
                        "プログラムを終了したい場合はexitと入力してください");
                Scanner scanner = new Scanner(System.in);
                while(true) {
                    String inputText = scanner.nextLine();
                    if(inputText.equals("exit")) {
                        System.exit(0);
                    }
                    if(inputText.equals("y")) {
                        break;
                    }
                }
            }
            int battleTagLocation = -1;
            int firstChoiceRoleLocation = 0;
            int secondChoiceRoleLocation = 0;
            int thirdChoiceRoleLocation = 0;
            int tankRateLocation = 0;
            int damageRateLocation = 0;
            int supportRateLocation = 0;
            try {
                tableContents = br.readLine();
            } catch (IOException e) {
                throw new RuntimeException("errorCode 001\n目次が見つかりませんでした\nファイルを確認してください");
            }
            battleTagLocation = countCommasBeforeTarget(tableContents,"バトルタグ");
            if(battleTagLocation == -1) {
                throw new IllegalArgumentException("errorCode 002\nバトルタグが見つかりませんでした\nファイルを確認してください");
            }
            firstChoiceRoleLocation = countCommasBeforeTarget(tableContents,"第一希望ロール");
            if(firstChoiceRoleLocation == -1) {
                throw  new IllegalArgumentException("errorCode 003\n第一希望ロールが見つかりませんでした\nファイルを確認してください");
            }
            secondChoiceRoleLocation = countCommasBeforeTarget(tableContents,"第二希望ロール");
            thirdChoiceRoleLocation = countCommasBeforeTarget(tableContents,"第三希望ロール");
            tankRateLocation = countCommasBeforeTarget(tableContents,"タンクのレート");
            damageRateLocation = countCommasBeforeTarget(tableContents,"ダメージのレート");
            supportRateLocation = countCommasBeforeTarget(tableContents,"サポートのレート");
            while(true) {
                int[] rate = {-1,-1,-1};
                try {
                    tableContents = br.readLine();
                }catch (IOException e){
                    throw new IllegalArgumentException("errorCode 000");
                }
                if(tableContents == null) {
                    break;
                }
                int num = -1;
                String s = "";
                num = findIndexOfNthComma(tableContents,tankRateLocation);
                s = getStringBetweenCommas(tableContents,tankRateLocation);
                if(num != -1 && !s.equals("")) {
                    try{
                        rate[0] = Integer.parseInt(s);
                    }catch (NumberFormatException e){
                        System.out.println("errorCode 004" + s);
                    }
                }
                num = findIndexOfNthComma(tableContents,damageRateLocation);
                s = getStringBetweenCommas(tableContents,damageRateLocation);
                if(num != -1 && !s.equals("")) {
                    try{
                        rate[1] = Integer.parseInt(s);
                    }catch (NumberFormatException e) {
                        System.out.println("errorCode 005" + s);
                    }
                }
                num = findIndexOfNthComma(tableContents,supportRateLocation);
                s = getStringBetweenCommas(tableContents,supportRateLocation);
                if(num != -1 && !s.equals("")) {
                    try{
                        rate[2] = Integer.parseInt(s);
                    }catch (NumberFormatException e) {
                        System.out.println("errorCode 006" + s);
                    }
                }
                int[] desiredRoleOrder = {-1,-1,-1};
                num = findIndexOfNthComma(tableContents,firstChoiceRoleLocation);
                s = getStringBetweenCommas(tableContents,firstChoiceRoleLocation);
                if(num != -1 && !s.equals("")) {
                    switch (s) {
                        case "タンク" :
                            desiredRoleOrder[0] = 1;
                            break;
                        case "ダメージ" :
                            desiredRoleOrder[1] = 1;
                            break;
                        case "サポート" :
                            desiredRoleOrder[2] = 1;
                            break;
                        default:
                            throw new IllegalArgumentException("errorCode 008\n第一希望ロールが確認できませんでした\n" +
                                    getStringBetweenCommas(tableContents,battleTagLocation));
                    }
                }
                num = findIndexOfNthComma(tableContents,secondChoiceRoleLocation);
                s = getStringBetweenCommas(tableContents,secondChoiceRoleLocation);
                if(num != -1 && !s.equals("")) {
                    switch (s) {
                        case "タンク" :
                            desiredRoleOrder[0] = 2;
                            break;
                        case "ダメージ" :
                            desiredRoleOrder[1] = 2;
                            break;
                        case "サポート" :
                            desiredRoleOrder[2] = 2;
                            break;
                    }
                }
                num = findIndexOfNthComma(tableContents,thirdChoiceRoleLocation);
                s = getStringBetweenCommas(tableContents,thirdChoiceRoleLocation);
                if(num != -1 && !s.equals("")) {
                    switch (s) {
                        case "タンク" :
                            desiredRoleOrder[0] = 3;
                            break;
                        case "ダメージ" :
                            desiredRoleOrder[1] = 3;
                            break;
                        case "サポート" :
                            desiredRoleOrder[2] = 3;
                            break;
                    }
                }
                num = findIndexOfNthComma(tableContents,battleTagLocation);
                String battleTag = getStringBetweenCommas(tableContents,battleTagLocation);
                if(num == -1 || battleTag.equals("")) {
                    throw new IllegalArgumentException("errorCode007");
                }
                MemberList.add(new Member(battleTag,desiredRoleOrder,rate));
            }
                return new ArrayList<TeamData.Member>(MemberList);
        }
    }
    private static int countCommasBeforeTarget(String tableContents,String s) {
        int count = 0;
        int targetIndex = tableContents.indexOf(s);
        if (targetIndex == -1) {
            return -1; // 指定の文字列が見つからない場合
        }
        for (int i = 0; i < targetIndex; i++) {
            if (tableContents.charAt(i) == ',') {
                count++;
            }
        }
        return count;
    }
    private static int findIndexOfNthComma(String tableContents, int n) {
        int count = 0;
        for (int i = 0; i < tableContents.length(); i++) {
            if (tableContents.charAt(i) == ',') {
                count++;
                if (count == n) {
                    return i;
                }
            }
        }
        return -1; // 指定された回数だけカンマが見つからない場合
    }
    private static String getStringBetweenCommas(String tableContents, int n) {
        int startIndex = findIndexOfNthComma(tableContents, n);
        int endIndex = findIndexOfNthComma(tableContents, n + 1);

        if (startIndex == -1 || endIndex == -1) {
            return null; // カンマが足りない場合
        }

        return tableContents.substring(startIndex + 1, endIndex);
    }
}
