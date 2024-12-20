package projet_java;

import java.util.Scanner;

public class SupprimeMenu {

    public static void Supprime(Scanner scanner) {
        System.out.println("请选择删除操作：");
        System.out.println("1. 删除学生数据");
        System.out.println("2. 删除人员数据");
        System.out.println("3. 删除研究员数据");
        System.out.println("4. 删除MCF数据");
        System.out.println("5. 删除导师数据");
        System.out.println("6. 删除导师学科数据");
        System.out.println("7. 返回上一菜单");
        System.out.print("请选择一个选项 (1-7): ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // 清除换行符

        switch (choice) {
            case 1:
                System.out.print("请输入学生ID (可选): ");
                Integer etudiantID = parseNullableInput(scanner.nextLine().trim(), Integer.class);
                System.out.print("请输入论文题目 (可选): ");
                String sujetDeThese = parseNullableInput(scanner.nextLine().trim(), String.class);
                System.out.print("请输入学科编号 (可选): ");
                Integer disciplineId = parseNullableInput(scanner.nextLine().trim(), Integer.class);
                System.out.print("请输入论文年份 (可选): ");
                Integer anneeDeThese = parseNullableInput(scanner.nextLine().trim(), Integer.class);
                System.out.print("请输入导师ID (可选): ");
                Integer encadrantId = parseNullableInput(scanner.nextLine().trim(), Integer.class);

                DeleteOperations.SupprimeEtudiant(etudiantID, sujetDeThese, disciplineId, anneeDeThese, encadrantId);
                break;
            case 2:
                System.out.print("请输入人员ID (可选): ");
                Integer personneID = parseNullableInput(scanner.nextLine().trim(), Integer.class);
                System.out.print("请输入姓名 (可选): ");
                String nom = parseNullableInput(scanner.nextLine().trim(), String.class);
                System.out.print("请输入姓氏 (可选): ");
                String prenom = parseNullableInput(scanner.nextLine().trim(), String.class);
                System.out.print("请输入年龄 (可选): ");
                Integer age = parseNullableInput(scanner.nextLine().trim(), Integer.class);
                System.out.print("请输入城市 (可选): ");
                String ville = parseNullableInput(scanner.nextLine().trim(), String.class);

                DeleteOperations.SupprimePersonne(personneID, nom, prenom, age, ville);
                break;
            case 3:
                System.out.print("请输入研究员ID (可选): ");
                Integer chercheurID = parseNullableInput(scanner.nextLine().trim(), Integer.class);
                System.out.print("请输入学生ID (可选): ");
                Integer chercheurEtudiantID = parseNullableInput(scanner.nextLine().trim(), Integer.class);

                DeleteOperations.SupprimeChercheur(chercheurID, chercheurEtudiantID);
                break;
            case 4:
                System.out.print("请输入MCF ID (可选): ");
                Integer mcfID = parseNullableInput(scanner.nextLine().trim(), Integer.class);
                System.out.print("请输入学生ID (可选): ");
                Integer mcfEtudiantID = parseNullableInput(scanner.nextLine().trim(), Integer.class);

                DeleteOperations.SupprimeMCF(mcfID, mcfEtudiantID);
                break;
            case 5:
                System.out.print("请输入导师ID (可选): ");
                Integer titulaireID = parseNullableInput(scanner.nextLine().trim(), Integer.class);
                System.out.print("请输入办公室编号 (可选): ");
                Integer titulaireNumbureau = parseNullableInput(scanner.nextLine().trim(), Integer.class);

                DeleteOperations.SupprimeTitulaire(titulaireID, titulaireNumbureau);
                break;
            case 6:
                System.out.print("请输入导师学科ID (可选): ");
                Integer disciplineID = parseNullableInput(scanner.nextLine().trim(), Integer.class);
                System.out.print("请输入学科编号 (可选): ");
                Integer titulaireDisciplineID = parseNullableInput(scanner.nextLine().trim(), Integer.class);

                DeleteOperations.SupprimeTitulaire_Discipline(disciplineID, titulaireDisciplineID);
                break;
            case 7:
                System.out.println("返回上一菜单...");
                break;
            default:
                System.out.println("无效的选择！");
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T parseNullableInput(String input, Class<T> type) {
        if (input.isEmpty()) {
            return null;
        }

        try {
            if (type == Integer.class) {
                return (T) Integer.valueOf(input);
            } else if (type == String.class) {
                return (T) input;
            }
        } catch (Exception e) {
            System.out.println("无效的输入：" + input + "，将被忽略。");
        }

        return null;
    }
}
