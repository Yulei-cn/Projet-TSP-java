package projet_java;

import java.sql.Connection;
import java.util.*;

import projet_java.Algo.*;
import projet_java.Geographie.*;
import projet_java.Personne.*;

public class UpdateMenu {

    public static void executeUpdateMenu(Scanner scanner) {
        System.out.println("请选择更新操作：");
        System.out.println("1. 更新学生数据");
        System.out.println("2. 更新人员数据");
        System.out.println("3. 更新研究员数据");
        System.out.println("4. 更新MCF数据");
        System.out.println("5. 更新导师数据");
        System.out.println("6. 更新导师学科数据");
        System.out.println("7. 返回上一菜单");
        System.out.print("请选择一个选项 (1-7): ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // 清除换行符

        switch (choice) {
            case 1:
                updateEtudiant(scanner);
                break;
            case 2:
                updatePersonne(scanner);
                break;
            case 3:
                updateChercheur(scanner);
                break;
            case 4:
                updateMCF(scanner);
                break;
            case 5:
                updateTitulaire(scanner);
                break;
            case 6:
                updateTitulaireDiscipline(scanner);
                break;
            case 7:
                System.out.println("返回上一菜单...");
                break;
            default:
                System.out.println("无效的选择！");
        }
    }

    private static void updateEtudiant(Scanner scanner) {
        System.out.println("请输入更新条件和新值：");

        System.out.print("现有的学生ID (可选): ");
        Integer conpersonneId = parseNullableInput(scanner.nextLine().trim(), Integer.class);

        System.out.print("现有的论文题目 (可选): ");
        String consujetDeThese = parseNullableInput(scanner.nextLine().trim(), String.class);

        System.out.print("现有的学科编号 (可选): ");
        Integer condisciplineId = parseNullableInput(scanner.nextLine().trim(), Integer.class);

        System.out.print("现有的论文年份 (可选): ");
        Integer conanneeDeThese = parseNullableInput(scanner.nextLine().trim(), Integer.class);

        System.out.print("现有的导师ID (可选): ");
        Integer conencadrantId = parseNullableInput(scanner.nextLine().trim(), Integer.class);

        System.out.print("新论文题目 (可选): ");
        String sujetDeThese = parseNullableInput(scanner.nextLine().trim(), String.class);

        System.out.print("新学科编号 (可选): ");
        Integer disciplineId = parseNullableInput(scanner.nextLine().trim(), Integer.class);

        System.out.print("新论文年份 (可选): ");
        Integer anneeDeThese = parseNullableInput(scanner.nextLine().trim(), Integer.class);

        System.out.print("新导师ID (可选): ");
        Integer encadrantId = parseNullableInput(scanner.nextLine().trim(), Integer.class);

        Etudiant.UpdateEtudiant(sujetDeThese, disciplineId, anneeDeThese, encadrantId, conpersonneId, consujetDeThese, condisciplineId, conanneeDeThese, conencadrantId);
    }

    // 其他更新方法和 `parseNullableInput` 保持不变
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

    private static void updatePersonne(Scanner scanner) { /* 保留原有逻辑 */ }
    private static void updateChercheur(Scanner scanner) { /* 保留原有逻辑 */ }
    private static void updateMCF(Scanner scanner) { /* 保留原有逻辑 */ }
    private static void updateTitulaire(Scanner scanner) { /* 保留原有逻辑 */ }
    private static void updateTitulaireDiscipline(Scanner scanner) { /* 保留原有逻辑 */ }
}
