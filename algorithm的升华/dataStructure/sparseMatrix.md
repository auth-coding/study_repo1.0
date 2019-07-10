# 稀疏矩阵

### 1 应用场景

在计算机中，一般使用二维数组存储矩阵，当矩阵中0的数目占了大多数，使用二维数组存储之时，就会存储大量重复的数字—0，从而造成内存浪费,从而就会引发思考，怎么去存储此类重复数据，稀疏矩阵这种数据结构也就应运而生。

### 2 定义

* 线性代数中定义

  在矩阵中，若数值为0的元素数目远远多于非0元素的数目，并且非0元素分布没有规律时，则称该矩阵为稀疏矩阵；与之相反，若非0元素数目占大多数时，则称该矩阵为稠密矩阵。定义非零元素的总数比上矩阵所有元素的总数为矩阵的稠密度。

* 数据结构中的定义

  数据结构中的稀疏矩阵，也叫稀疏数组，按特定组织方式存储的稀疏矩阵的二维数组。

### 3 原理分析

 #### 3.1 二维数组转稀疏数组

稀疏数组存储二维数组的原理是存储二维数组中的有效数据，可想而知，由于二维数组的特性，存储二维数组中的一个元素，则需要存储元素的所在行列和值，因此稀疏数组是一个固定为三列的二维数组。

怎么确定稀疏数组的行数，取决于有效数据的个数，但是二维数组有效数据的个数事先并不能确认，并且考虑到还需要稀疏数组转回二维数组，所以二维数组的行列都需要存储，考虑到稀疏数组是三列，所以最后一列可以存储有效数据的个数。即第一行三列分别存储二维数组行、列、有效数据个数(至于为什么存储在稀疏矩阵第一行—在未知的数组下， 只有第一行的位置是确定的)。

因此先循环二维数组，得到有效数据个数sum，创建稀疏数组` sparse[sum+1][3]`,可根据二维数组的行列存储到稀疏数组第一行。

再次循环，存储有效数据的行列和值，得到稀疏数组。

#### 3.2 稀疏数组转二维数组

稀疏数组转二维数组，类似之前的过程，读取稀疏数组第一行数据创建二维数组，并全部设置默认值为0，接着读取稀疏数组剩余行的数据，根据读到的行列改变二维数组中的对应元素值。



### 4 java代码实现

```java
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
```





### 5 总结

稀疏数组(矩阵)是对概念上的稀疏矩阵以一种更节省内存的方式存储，能够实现普通二维数组和稀疏数组的相互转换。

如果稀疏数组的转换对象不是稀疏矩阵或者起不到节省内存的作用，那就没有必要以稀疏数组的方式存储。

稀疏数组约定俗称，第一行存储二维数组的行、列、有效数据的个数，剩余行存储有效数据的行、列、值。