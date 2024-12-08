DAY_NUM=$1
INPUT=$2

FILE_NAME=Day$DAY_NUM
cd ./resources || exit
echo "$INPUT" > "$FILE_NAME.txt"
git add "$FILE_NAME.txt"

cd ../kotlin || exit


echo "private const val fileName = \"./src/main/resources/$FILE_NAME.txt\"

fun main() {
   partOne()
   partTwo()
}

private fun partOne() {

}

private fun partTwo() {

}" > "$FILE_NAME.kt"

git add ../kotlin/"$FILE_NAME.kt"