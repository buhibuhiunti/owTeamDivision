import TeamData.Member;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class LoadDatasheet {

    static List<TeamData.Member> loadDatasheet() {
        String tableContents;
        List<TeamData.Member> MemberList = new ArrayList<TeamData.Member>();
        while(true) {
            try {


                File f = new File("memberList.csv");
                BufferedReader br = new BufferedReader((new FileReader(f)));

                int battleTagLocation = 0;
                int firstChoiceRoleLocation = 0;
                int secondChoiceRoleLocation = 0;
                int thirdChoiceRoleLocation = 0;
                int tankRateLocation = 0;
                int damageRateLocation = 0;
                int supportRateLocation = 0;
                if ((tableContents = br.readLine()) == null) {
                    System.out.println("errorCode 001\n目次が見つかりませんでした\nファイルを確認してください");
                } else {
                    int battleTagIndexOf = tableContents.indexOf("バトルタグ");
                    if(battleTagIndexOf == -1) {
                        System.out.println("errorCode 001\nバトルタグが見つかりませんでした\nファイルを確認してください");
                        System.exit(0);
                    }

                    for(int i = 0; i <= battleTagIndexOf; i++) {
                        if(tableContents.charAt(i) == ',') {
                            battleTagLocation++;
                        }
                    }

                    int firstChoiceRoleIndexOf = tableContents.indexOf("第一希望ロール");
                    if(firstChoiceRoleIndexOf == -1) {
                        System.out.println("errorCode 001\n第一希望ロールが見つかりませんでした\nファイルを確認してください");
                        System.exit(0);
                    }

                    for(int i = 0;i <= firstChoiceRoleIndexOf;i++) {
                        if(tableContents.charAt(i) == ',') {
                            firstChoiceRoleLocation++;
                        }
                    }

                    secondChoiceRoleLocation = searchItem("第二希望ロール",tableContents);

                    thirdChoiceRoleLocation = searchItem("第三希望ロール",tableContents);

                    tankRateLocation = searchItem("タンクのレート",tableContents);

                    damageRateLocation = searchItem("ダメージのレート",tableContents);

                    supportRateLocation = searchItem("サポートのレート",tableContents);
                }
                while(true) {
                    try {
                        tableContents = br.readLine();
                    }
                    catch (IOException e){
                        break;
                    }
                    int[] desiredRoleOrder = {-1,-1,-1};
                    int[] rate = {-1,-1,-1};
                    String s = tableContents.substring(searchIndex(firstChoiceRoleLocation, tableContents)+1,
                                    searchIndex(firstChoiceRoleLocation++,tableContents));
                    if(s.equals("タンク")) {
                        desiredRoleOrder[0] = 1;
                    }
                    if(s.equals("ダメージ")) {
                        desiredRoleOrder[1] = 1;
                    }
                    if(s.equals("サポート")) {
                        desiredRoleOrder[2] = 1;
                    }
                    s = tableContents.substring(searchIndex(secondChoiceRoleLocation,tableContents)+1,
                                    searchIndex(secondChoiceRoleLocation++,tableContents));
                    if(s.equals("タンク")) {
                        desiredRoleOrder[0] = 2;
                    }
                    if(s.equals("ダメージ")) {
                        desiredRoleOrder[1] = 2;
                    }
                    if(s.equals("サポート")) {
                        desiredRoleOrder[2] = 2;
                    }
                    s = tableContents.substring(searchIndex(thirdChoiceRoleLocation,tableContents)+1,
                                    searchIndex(thirdChoiceRoleLocation,tableContents));
                    if(s.equals("タンク")) {
                        desiredRoleOrder[0] = 3;
                    }
                    if(s.equals("ダメージ")) {
                        desiredRoleOrder[1] = 3;
                    }
                    if(s.equals("サポート")) {
                        desiredRoleOrder[2] = 3;
                    }
                    rate[0] = Integer.parseInt(tableContents.substring(searchIndex(tankRateLocation,tableContents)+1,
                                searchIndex(tankRateLocation++,tableContents)));
                    rate[1] = Integer.parseInt(tableContents.substring(searchIndex(damageRateLocation,tableContents)+1,
                                searchIndex(damageRateLocation++,tableContents)));
                    rate[2] = Integer.parseInt(tableContents.substring(searchIndex(supportRateLocation,tableContents)+1,
                                searchIndex(supportRateLocation,tableContents)));
                    MemberList.add(new TeamData.Member(tableContents.substring(searchIndex(battleTagLocation,tableContents)+1,
                                    searchIndex(battleTagLocation++,tableContents)),desiredRoleOrder,rate));
                    }

                return new ArrayList<TeamData.Member>(MemberList);
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
        }
    }
    private static int searchItem(String s, String tableContents) {
        int sIndexOf = tableContents.indexOf(s);
        int location = 0;
        if(!(sIndexOf == -1)) {
            for(int i = 0;i <= sIndexOf;i++) {
                if(tableContents.charAt(i) == ',') {
                    location++;
                }
            }
            return location;
        }
        return -1;
    }
    private static int searchIndex(int location, String tableContents) {
        int searchWordIndex = 0;
        for(int i = 0;i <= location;i++) {
            searchWordIndex = tableContents.indexOf(",",searchWordIndex);
        }
        return searchWordIndex;
    }
}
