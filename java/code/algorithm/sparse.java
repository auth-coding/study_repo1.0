/*
数据结构--稀疏矩阵
author@liyi
date@2019/7/1
 */

class Sparse{
    public static void main(String[] args) {
        // 先创建二维数组， 由二维数组转化为稀疏矩阵
        int chess[][] = new int[11][11];
        chess[2][3] = 1;
        chess[3][4] = 2;
        // 输出二维数组 且统计有效数据
        int sum = 0 ;
        for (int i=0; i<11; i++
        ){
            for (int j=0; j<11; j++){
                if (chess[i][j] != 0){
                    sum++ ;
                }
                System.out.printf("%d\t", chess[i][j]);
            }
            System.out.println();
        }
        // 转换为二维数组
        int sparseArray[][] = new int[sum+1][3];
        sparseArray[0][0] = 11 ; //行
        sparseArray[0][1] = 11 ; // 列
        sparseArray[0][2] = sum ; // 有效数据个数
        int index = 0;
        for (int i=0; i<chess.length; i++){
            for (int j=0; j<chess[0].length; j++){
                if (chess[i][j] != 0){
                    index++ ;
                    sparseArray[index][0] = i;
                    sparseArray[index][1] = j;
                    sparseArray[index][2] = chess[i][j];
                }
            }
        }
        // 输出稀疏矩阵
        for (int row[]: sparseArray){
            for (int item: row){
                System.out.printf("%d\t", item);
            }
            System.out.println();
        }
        // 稀疏矩阵转二维数组
        int load_chess[][] = new int[sparseArray[0][0]][sparseArray[0][1]];
        for (int i=1; i<sparseArray.length; i++){
            load_chess[sparseArray[i][0]][sparseArray[i][1]] = sparseArray[i][2];
        }
        // 输出二维数组
        for (int row[]:load_chess){
            for (int item: row){
                System.out.printf("%d\t", item);
            }
            System.out.println();
        }
    }


}