package com.example.demo.LeetCode;

import com.sun.org.apache.bcel.internal.generic.RET;

import java.util.*;

class ListNode {
      int val;
      ListNode next;
      ListNode(int x) { val = x; }
  }
public class Solution {
     //
    public static ListNode addTwoNumbers(ListNode l1 ,ListNode l2){
        ListNode dummyHead = new ListNode(0);
        ListNode p = l1 ,q = l2,curr = dummyHead;
        int carry = 0;
        while (p!=null || q!=null){
            int x = (p != null) ? p.val :0;
            int y = (q !=null) ? q.val : 0;
            int sum = carry + x+y;
            carry = sum /10 ;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
            if (p != null) p = p.next;
            if (q != null) q = q.next;
        }
        if (carry > 0){
            curr.next = new ListNode(carry);
        }
        return dummyHead.next;
    }
    public static void createNodeList(){
        ListNode l1 = new ListNode(2);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(3);
        ListNode l2 = new ListNode(5);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(4);

        ListNode l3= addTwoNumbers(l1,l2);
        while (l3 != null){
            System.out.println(l3.val);
            l3 = l3.next;
        }
    }
    //最长无重复子串
    public int lengthOfLongestSubstring(String s) {
        int n = s.length();
        Set<Character> set = new HashSet<>();
        int ans = 0 ,j = 0, i=0;
        while (i < n && j <n){
            if (!set.contains(s.charAt(j))){
                set.add(s.charAt(j++));
                ans  = Math.max(ans,j -i);
            }else {
                set.remove(s.charAt(i++));
            }
        }
        return ans;
    } 
    public int lengthOfLongestSubstring2(String s) {
        int n = s.length();
        int ans  =0 ;
        Map<Character ,Integer> map = new HashMap<>();
        for (int j = 0 ,i = 0 ;j<n ; j++){
            if (map.containsKey(s.charAt(j))){
                i = Math.max(map.get(s.charAt(j)),i);
            }
            ans = Math.max(ans,j - 1 +1);
            map.put(s.charAt(j) ,j+1);
        }
        return ans;
    }
    //最长回文子传
    public String longestPalindrome(String s) {
        int len = s.length();
        String str ;
        String str2 = "";
        List<String> list = new ArrayList<>();
        int i =0, j=0;
        for (;i <s.length();i++){
            for (;j>i;j++){
                str =(s.substring(i,j));
                for (int n = 0;  n< str.length()/2;n++){
                    if (!(str.charAt(n) == str.charAt(str.length() -n))){
                        break;
                    }
                    if (n > str.length()/2){
                        if (str.length()>str2.length())
                            str2 = str;
                    }
                }
            } 
        }
        return str2;
    }
    //z字型变换
    public String z_convert(String s, int numRows) {
        if (numRows <=1 ){
            return s;
        }
        List<StringBuilder> list = new ArrayList<>();
        for (int i =0 ;i<Math.min(s.length(),numRows);i++){
            list.add(new StringBuilder());
        }
        boolean turnGo = false;
        int curRow = 0;
        for (char c  : s.toCharArray()){
            list.get(curRow).append(c);
            if (curRow == 0 || curRow == numRows -1){ 
                turnGo = !turnGo;
            }
            curRow += turnGo ? 1 :-1; //false 时候向下：加 true的时候向上:减
        }
        StringBuilder ret = new StringBuilder();
        for (StringBuilder row : list){
            ret.append(row);
        }
        return ret.toString();
    }
    //整数反转
    public int reverse(int x) {
        int rev =0;
        while (x != 0){
            int pop = x % 10;
            x = x/10 ;
            if (rev > Integer.MAX_VALUE /10 || (rev == Integer.MAX_VALUE / 10 && pop > 7)) return 0;
            if (rev < Integer.MIN_VALUE /10 || (rev == Integer.MIN_VALUE /10 && pop < -8)) return 0;
            rev = rev * 10 + pop;
        }
        return rev;
    }
    //atoi 函数，使其能将字符串转换成整数。
    public int myAtoi(String str) {
        if (str.length() == 0){
            return 0;
        }
        int i =0;
        while (str.charAt(i) ==' '){
            i++;
        }
        boolean minut  = false;
        int num = 0;
        if (str.charAt(i) =='-'){
            minut= true;
            i++;
            if (str.charAt(i)<'0' || str.charAt(i) >'9')
                return num;
        }
        if (str.charAt(i) == '+'){
            minut = false;
            i++;
            if (str.charAt(i) <'0' || str.charAt(i) >'9'){
                return num;
            }
        }
        while (str.charAt(i) !='\0' && str.charAt(i) >= '0' && str.charAt(i)<='9'){
            num = num * 10 + Integer.valueOf(str.charAt(i));
            //检查溢出
            if (num > Integer.MAX_VALUE && minut == true){
                return Integer.MIN_VALUE;
            }
            if (minut == false && num > Integer.MAX_VALUE){
                return  Integer.MAX_VALUE;
            }
        }
        if (minut==true){
            return 0-num;
        }
        return num;
    }
    //判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
    public boolean isPalindrome(int x) {
        if (x < 0 || (x % 10 ==0 && x !=0)){
            return false;
        }
        int res = 0;
        while (x > res){
            res = res * 10 + x %10;
            x /= 10;
        }
        return x == res || x == res /10;
    }
    //两线段之间形成的区域总是会受到其中较短那条长度的限制。此外，两线段距离越远，得到的面积就越大。
    public int maxArea(int[] height) {
        int max =0 , l = 0,r = height.length -1;
        while (l<r){
            max = Math.max(max,Math.min(height[l],height[r]) * (r-l));
            if (height[r]> height[l]){
                l++;
            }else {
                r--;
            }
        }
        return max;
    }
    //整数转罗马数字
    public static String intToRoman(int num) {
        int[] nums = {1000,900,500,400,100,90,50,40,10,9,5,4,1};
        String[] romans = {"M","CM","D","CD","C","XC","L","CL","X","IX","V","IV","I"};
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <  nums.length; i++) {
            while (num >= nums[i]){
                sb.append(romans[i]);
                num -= nums[i];
            }
        }
        return sb.toString();
    }
    public static int romanToInt(String s) {
        int[] nums = {1000,900,500,400,100,90,50,40,10,9,5,4,1};
        String[] romans = {"M","CM","D","CD","C","XC","L","CL","X","IX","V","IV","I"};
        int num = 0;
       while (!"".equals(s) && s!=null){
            for (int j = 0; j < romans.length; j++) {
                if (s.startsWith(romans[j])){
                    num += nums[j];
                    s = s.substring(romans[j].length(),s.length());
                }
            }
       }

        return num;
    }
    public static int romanToInt2(String s) {
        int[] nums = {1000,900,500,400,100,90,50,40,10,9,5,4,1};
        String[] romans = {"M","CM","D","CD","C","XC","L","CL","X","IX","V","IV","I"};
        Map<String,Integer> ha = new HashMap<>();
        ha.put("M",1000);
        ha.put("CM",900);
        ha.put("D",500);
        ha.put("CD",400);
        ha.put("C",100);
        ha.put("XC",90);
        ha.put("L",50);
        ha.put("CL",40);
        ha.put("X",10);
        ha.put("IX",9);
        ha.put("V",5);
        ha.put("IV",4);
        ha.put("I",1);
        int num = 0;
        int curr ,last =0;
       for (Character c : s.toCharArray()){
           curr = ha.get(c);
           if (last >= curr){
               num += curr;
               
           }else {
               num += curr -last;
           }
           last = curr;
       }

        return num;
    }
    //最长公共前缀
    public String longestCommonPrefix(String[] strs) {
        String pre = strs[0];
        for (int i = 1 ;i<strs.length ;i++){
            while (strs[i].indexOf(pre) !=0){
                pre = pre.substring(0,pre.length() -1);
                if (pre.isEmpty()){
                    return "";
                }
                
            }
        }
        return pre;
    }
    
    public static List<List<Integer>> threeSum(int[] nums) {
//        List<List<Integer>> res = new ArrayList<>();
//       
//        for (int i = 0; i < nums.length; i++) {
//            for (int j = i+1; j < nums.length; j++) {
//                for (int k = j+1; k < nums.length; k++) {
//                    List<Integer> list = new ArrayList<>();
//                    if ((nums[i]+ nums[j]+nums[k]) ==0){
//                        list.add(nums[i]);
//                        list.add(nums[j]);
//                        list.add(nums[k]);
//                        res.add(list);
//                    }
//                }
//            }
//        }
//        return res;
        Arrays.sort(nums);
        List<List<Integer>> ls = new ArrayList<List<Integer>>();
        for (int i = 0; i < nums.length ; i++) {
            if (i == 0 || (i >0 && nums[i] != nums[i -1 ])){ // 跳过重复值
                int  l = i+1,r= nums.length -1 ,sum = 0 -nums[i];
                while (l<r){
                    if (nums[l] + nums[r] == sum){
                        ls.add(Arrays.asList(nums[i] , nums[l] ,nums[r]));
                        while (l < r && nums[l] == nums[l+1]) l++; // 跳过重复值
                        while (l < r && nums[r] == nums[r-1]) r--;
                        l++;
                        r--;
                    }else if (nums[l] + nums[r] < sum){
                        while (l < r && nums[l] == nums[l+1]) l++;
                        l++;
                    }else {
                        while (l < r && nums[r] == nums[r-1]) r--;
                        r--;
                    }
                }
            }
        }
        return ls;
    }
    public static List<String> letterCombinations(String digits) {
        List<String> list = new ArrayList<String>();
        if (digits.length() ==0){
            return list;
        }
       
        Map<String ,String> map = new HashMap<>();
        map.put("2","abc");
        map.put("3","def");
        map.put("4","ghi");
        map.put("5","jkl");
        map.put("6","mno");
        map.put("7","qprs");
        map.put("8","tuv");
        map.put("9","wxxz");
        list= getStringWithFor(digits,"",list,map);
        return list;
    }
    private static List<String> getStringWithFor(String s,String temp ,List<String> list,Map<String,String> map){
        if (s.length() ==0 ){ 
            list.add(temp);
            return list ;
        }
        String digit = s.substring(0,1);
        String str = map.get(digit);
        for (int i = 0; i <  str.length(); i++) { 
            String letter =  str.substring(i,i+1);
            list = getStringWithFor(s.substring(1),temp+ letter,list,map);
        }
        return list;
    }

    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> list = new ArrayList<>();
        Arrays.sort(nums);
        int iNum = 0;
        int last = 0;
        for (int i = 0; i < nums.length -4; i++) {
            if ( nums[i] == nums[i+1]){
                continue;
            }
            
        }
        
        return list;
    }
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy =  new ListNode(0);
        dummy.next = head;
        ListNode node = head;
        int i =0;
        while (node.next != null){
            i++;
            node = node.next;
        }
        i -=n;
        node = dummy;
        while (i>0){
            i--;
            node = node.next;
        }
        node.next = node.next.next;
        return dummy.next;
    }
    public boolean isValid(String s) {
        if (s == ""){
            return true;
        }
        boolean resu = false;
        HashMap<String,Integer> map = new HashMap<>();
        map.put("({[",0);
        map.put(")}]",0);
        int[] ss = new int[]{0,0,0};
        for (char c : s.toCharArray()){
            if (map.containsKey(c)){
                
            }
        }
        return  false;
        
    }
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode listNode = new ListNode(0);
        ListNode headNode = listNode.next;
        while (l1 !=null && l2 != null){
            if (l1.val < l2.val){
                listNode.next = l1;
                l1 = l1.next;
                listNode = listNode.next;
            }else {
                listNode = l2;
                l2 = l2.next;
                listNode = listNode.next;
            }
        }
        if (l1 == null){
            listNode.next = l1;
        }else {
            listNode.next = l2;
        }
        return headNode.next;
    }
    public static List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList<>();
        backtrack(ans,"",0,0,n);
        return ans;
    }

    private static void backtrack(List<String> ans, String cur, int open, int close, int max) {
        if (cur.length() == max * 2){
            ans.add(cur);
            return;
        }
        if (open < max){
            backtrack(ans,cur+"(",open +1 ,close,max);
        }
        if (close < max){
            backtrack(ans,cur+")",open,close+1,max);
        }
    }
    public static List<String> generateParenthesis2(int n) {
        List<String> combinations = new ArrayList<>();
        generateAll(new char[2 * n],0,combinations);
        return combinations;
    }

    private static void generateAll(char[] current, int pos, List<String> result) {
        if (pos == current.length){
            if (thesisvalid(current)){
                result.add(new String(current));
            }
        }else {
            current[pos] = '(';
            generateAll(current,pos +1 ,result);
            current[pos] = ')';
            generateAll(current,pos +1 ,result);
        }
    }

    private static boolean thesisvalid(char[] current) {
        int balance = 0;
        for (char c: current){
            if (c == '(') 
                balance++;
            else 
                balance--;
            if (balance<0) 
                return false;
        }
        return (balance==0);
    }

    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next ==null){
            return head;
        }
        ListNode node = head.next;
        head.next = swapPairs(node.next);
        node.next = head;
        return node;
    }
    public ListNode reverseKGroup(ListNode head, int k) {
        if (k < 2){
            return head;
        }
        if (head == null) {
            return null;
        }
        int len = 0;
        ListNode cur = head;
        while (cur != null){
            cur = cur.next;
            len++;
        }
        if (k > len){
            return head;
        }
        ListNode res = new ListNode(0);
        res.next = head;
        /**前置指针、移动指针、后置指针**/
        ListNode pre = res;
        cur = head;
        ListNode next = head.next;
        for (int i = 0; i < len/k; i++) {
            for (int step = 1; step < k; step++) {
                cur.next = next.next;
                next.next = pre.next;
                pre.next = next;
                next = cur.next;
            }
            pre = cur;
            cur = next;
            if (next !=null){
                next = next.next;
            }
        }
        return res.next;
    }
    private static int removeDuplicatess(int[] nums) {
        Arrays.sort(nums);
        int leng = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[leng] != nums[i]){
                leng++;
                nums[leng] = nums[i];
                
            }
        }
        return leng;
    }
    public static int removeElement(int[] nums, int val) {
        int len = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val){
                nums[len] = nums[i];
                len++;
            }
        }
        return len;
    }
    public static int strStr(String haystack, String needle) {
        if (needle == null || needle == ""){
            return 0;
        }
        int start = -1;
        int j = 0;
        for (int i = 0; i < haystack.length(); i++) {
            for ( j = 0; j < needle.length(); j++) {
                if (haystack.charAt(i) == needle.charAt(j)){
                    start = i;
                }else {
                    start = -1;
                    break;
                }
            }
            if(start !=-1 && j >= needle.length()){
                return start;
            }
        }
        return start;
    }
    public int strStr2(String haystack, String needle) {
        if (needle.length() == 0){
            return 0;
        }
        if (needle.length() > haystack.length()){
            return -1;
        }
        int i=0,j=0;
        for ( i = 0; i < haystack.length(); i++) {
            if (j==needle.length()){
                return i - needle.length();
            }
            if (haystack.charAt(i) == needle.charAt(j)){
                j++;
            }else {
                i -= j ;
                j= 0;
            }
        }
        if (j == needle.length()){
            return i-needle.length();
        }
        return -1;
    }
    public int divide(int dividend, int divisor) {
        if (dividend == Integer.MAX_VALUE && divisor == -1){
            return Integer.MAX_VALUE;
        }
        int temp1 = Math.abs(dividend);
        int temp2 = Math.abs(divisor);
        if (temp1 < temp2){
            return 0;
        }
        int flag = 1;
        if ((dividend & divisor & Integer.MIN_VALUE) == 0 && (dividend < 0 || divisor <0))
            flag = -1;
        int i =1,last_i = 0;
        int d = temp2;
        if ( d ==1 ){
            i = temp1;
        }else {
            while (temp1 > d){
                d <<= 1;
                i <<= 1;
            }
            if (temp1 < d){
                i = (i >> 1) + divide(temp1 -(d >> 1),temp2);
            }
        }
        if (flag < 0)
            i = -1;
        return i;
    }
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> result = new ArrayList<>();
        if (words.length == 0){
            return result;
        }
        int LEN = words.length*words[0].length();
        int len = words[0].length();
        if (s.length() < len) {
            return result;
        }
        List<String> wlish = new ArrayList<>(Arrays.asList(words));
        int i=0;
        while (i+LEN -1 <s.length()){
            if (wlish.contains(s.substring(i,i+len))){
                List<String> tempList = new ArrayList<>(Arrays.asList(words));
                tempList.remove(s.substring(i,i+len));
                String tempString = s.substring(i+len,i+LEN);
                for (int j = 0; j < tempString.length(); j+=len) {
                    if (tempList.contains(tempString.substring(j,j+len))){
                        tempList.remove(tempString.substring(j,j+len));
                    }else {
                        break;
                    }
                }
                if (tempList.size() == 0){
                    result.add(i);
                }
            }
            i++;
        }
        return result;
    }
    public void nextPermutation(int[] nums) {
        int i = nums.length -2;
        while (i > 0 && nums[i -1] <= nums[i]){
            i--;
        }
        if (i > 0){
            int j =nums.length -1;
            while (j > 0 && nums[j] <= nums[i]){
                j--;
            }
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
        nextreverse(nums,i+1);
    }

    private void nextreverse(int[] nums, int start) {
        int i = start,j = nums.length -1;
        while (i < j){
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
            i++;
            j--;
        }
    }
    public int search(int[] nums, int target) {
        return search(nums,0,nums.length-1,target);
    }

    private int search(int[] nums, int low, int higt, int target) {
        if (low > higt){
            return -1;
        }
        int mid = (low + higt) /2 ;
        if (nums[mid] ==target){
            return  mid;
        }
        if (nums[mid] < nums[higt]){
            if (nums[mid] < target  && target <= nums[higt]){
                return search(nums,mid + 1, higt, target);
            }else {
                return search(nums, low, mid - 1, target);
            }
        }else {
            if (nums[low] <= target && target < nums[mid])
                return search(nums,low,mid -1 , target);
            else 
                return search(nums,mid+1,higt,target);
        }
    }
    
    public  int[] searchRange(int[] nums, int target)  {
        int left = 0 ; 
        int right = nums.length - 1;
        int[] res = new int[]{-1,-1};
         while (left <= right){
             int mid = (left + right) / 2;
             if (nums[mid] >= target){
                 right = mid - 1;
             }else {
                 left = mid + 1;
             }
         }
         if (left < nums.length && nums[left] == target){
             res[0] = left;
         }
         left = 0;
         right = nums.length -1;
         while (left <= right){
             int mid = (left + right) / 2;
             if (nums[mid] > target){
                 right = mid -1 ;
             }else {
                 left = mid + 1;
             }
         }
         if (right >= 0 && nums[right] == target){
             res[1] = right;
         }
         return res;
         
    }
    public int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length -1;
        if (target < nums[0]){
            return 0;
        }
        if (target > nums[right])
            return right+1;
        int mid;
        while (left <= right){
            mid = (left + right) /2;
            if (nums[mid] == target)
                return mid;
            if (target < nums[mid])
                right = mid -1;
            if (target > nums[mid]){
                left = mid + 1;
            }
        }
        if (nums[left] == target) return left;
        if (nums[right] == target) return right;
        return left;
    }
    public boolean isValidSudoku(char[][] board) {
        HashMap<Integer,Integer>[] rows = new HashMap[9];
        HashMap<Integer,Integer>[] columns = new HashMap[9];
        HashMap<Integer,Integer>[] boxs = new HashMap[9];
        for (int i = 0; i < 9; i++) {
            rows[i] = new HashMap<Integer, Integer>();
            columns[i] = new HashMap<Integer, Integer>();
            boxs[i] = new HashMap<Integer, Integer>();
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9 ; j++) {
                char num = board[i][j];
                if (num !='.'){
                    int n = (int)num;
                    int box_index = (i/3) * 3 + j/3;
                    rows[i].put(n,rows[i].getOrDefault(n,0)+1);
                    columns[j].put(n,columns[j].getOrDefault(n,0)+1);
                    boxs[box_index].put(n,boxs[box_index].getOrDefault(n,0)+1);
                    if (rows[i].get(n) > 1 || columns[j].get(n) > 1 || boxs[box_index].get(n) >1){
                        return false;
                    }
                }
            }
        }
        return true;
        
    }
    public String countAndSay(int n) {
        String res = "1";
        int t;
        //n次报数，循环n-1次
        for (int i = 0; i < n; i++) {
            t  = 0 ;
            StringBuilder temp = new StringBuilder();
            //对当前res里每个字符进行遍历，t表当前字符下标
            while (t < res.length()){
                int num = 1;
                //数连续且相同的数有几个，计入num
                while ((t+1)<res.length() && res.charAt(t) == res.charAt(t+1)){
                    t++;
                    num++;
                }
                //temp 最终为下一个报数生成值
                temp.append(num).append(res.charAt(t));
                t++;
            }
            //temp 复制res进行下一轮报数
            res = temp.toString();
        }
        return res;
    }
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> arrayList= new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        Arrays.sort(candidates);
        findCombinationSum(arrayList,list,candidates,target,0);
        return arrayList;
    }

    private void findCombinationSum(List<List<Integer>> listAll, List<Integer> temp, int[] candidates, int target, int num) {
        if (target == 0){
            listAll.add(temp);
            return;
        }
        if (target < candidates[num]) return;
        for (int i = num; i < candidates.length && candidates[i] <=target; i++) {
           // if (i>num && candidates[i] == candidates[i - 1]) continue;  //同一个数字不能重复 
            List<Integer> list = new ArrayList<>(temp);
            list.add(candidates[i]);
            findCombinationSum(listAll,list,candidates,target-candidates[i],i);
            //findCombinationSum(listAll,list,candidates,target-candidates[i],i+1);//同一个数字不能重复使用
            
        }
    }
    //雨水面积
    public int trap(int[] height) {
        int num = 0;
        int max = 0;
        int index = 0;
        //找到最高那个
        for (int i = 0; i < height.length; i++) {
            if (max < height[i]){
                max = height[i];
                index = i;
            }
        }
        //从左往右接水
        int left = 0;
        for (int i = 0; i < index; i++) {
            if (left > height[i]){
                num = num+ left-height[i];
            }else {
                left = height[i];
            }
        }
        //从右往左接水
        int rigth = 0;
        for (int i = height.length; i > index ; i--) {
            if (rigth > height[i]){
                num = num + rigth - height[i];
            }else {
                rigth = height[i];
            }
        }
        return num;
    }
    public String multiply(String num1, String num2) {
        int n1 = num1.length()-1;
        int n2 = num2.length()-1;
        if (n1 < 0 || n2 <0) return "";
        int[] mul = new int[n1+n2+2];
        for (int i = n1; i >=0 ; --i) {
            for (int j = n2; j >=0 ; --j) {
                int bitmul = (num1.charAt(i)-'0')*(num2.charAt(j)-'0');
                bitmul += mul[i+j+1]; //先加地位判断是否有新的进位
                mul[i+j] += bitmul / 10;
                mul[i+j+1] = bitmul % 10;
            }
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < mul.length-1 && mul[i] == 0){
            i++;
        }
        for (; i < mul.length ; ++i) {
            sb.append(mul[i]);
        }
        return sb.toString();
    }
    public int jump(int[] nums) {
        if (nums.length ==1) return 0;
        int reach = 0;
        int nextreach = nums[0];
        int step = 0;
        for (int i = 0; i < nums.length; i++) {
            nextreach = Math.max(i+nums[i],nextreach);
            if (nextreach >= nums.length -1) 
                return step+1;
            if ( i == reach){
                step ++;
                reach = nextreach;
            }
        }
        return step;
    }
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> listAll = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        permuteArr(listAll,list,nums,0);
        return listAll;
    }

    private void permuteArr(List<List<Integer>> listAll, List<Integer> list, int[] nums, int index) {
        if (nums.length == list.size()){
            listAll.add(new ArrayList<>(list));
            return;
        }
        for (int j = 0; j < nums.length  ; j++) {
            if (list.contains(nums[j])){
                continue;
            }
            list.add(nums[j]);
            permuteArr(listAll,list,nums,j);
            list.remove(list.size() -1);
        }
    }
    //顺时针90°旋转
    public void rotate(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length ==0){
            return;
        }
        int col = matrix[0].length -1;
        int row = matrix.length -1;
        int colF = col;
        int rowF = matrix.length -1;
        for (int r = 0; r < rowF; r++) {
            for (int c = 0; c < colF; c++) {
                int x1 = r;
                int y1 = c;
                int x2 = c;
                int y2 = col -r;
                int x3 = row - r;
                int y3 = col - c;
                int x4 = row - c ;
                int y4 =  r;
                swap(matrix,x1,y1,x2,y2);
                swap(matrix,x1,y1,x3,y3);
                swap(matrix,x1,y1,x4,y4);
            }
        }
    }

    private void swap(int[][] matrix, int x1, int y1, int x2, int y2) {
        int temp = matrix[x1][y1];
        matrix[x1][y1] = matrix[x2][y2];
        matrix[x2][y2] = temp;
    }
    
    public List<List<String>> groupAangrams(String[] strs){
        if (strs.length == 0) return new ArrayList<>();
        Map<String,List> ans = new HashMap<String, List>();
        for (String s: strs) {
            char[] ca = s.toCharArray();
            Arrays.sort(ca);
            String key = String.valueOf(ca);
            if (!ans.containsKey(key)) 
                ans.put(key, new ArrayList());
            ans.get(key).add(s);
        }
        return new ArrayList(ans.values());
    }
    public double myPow(double x, int n) {
        double res = 1.0;
        for (int i = n; i !=0; i/=2) {
            if (i % 2 !=0){
                res *=x;
            }
            x *=x;
        }
        return n<0 ? 1/res : res;
    }
    public static int maxSubArray(int[] nums) {
        int res = nums[0];
        int sum = 0;
        for (int num: nums ) {
            if (sum > 0){
                sum += num;
            }else {
                sum = num;
            }
            res = Math.max(res,sum);
        }
        return res;
    }
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> ans = new ArrayList<>();
        int m = matrix.length;
        if (m==0)
            return ans;
        int n = matrix[0].length;
        int visited[][] = new int[m][n];
        //计算出来一共要走几圈 存在time中
        int min,time =0;
        if (m > n) min =n;
        else min = m;
        if (min % 2 == 0){
            time = min /2;
        }else {
            time = min/2+1;
        }
        
        for (int i = 0; i < time; i++) {
            //左 - 右
            for (int j = i; j < n-i; j++) {
                if (visited[i][j] == 0){
                    ans.add(matrix[i][j]);
                    visited[i][j] =1;
                }
            }
            //右-下
            for (int j = i+1; j < m-1; j++) {
                if (visited[j][n-i-1] == 0){
                    ans.add(matrix[j][n-i-1]);
                    visited[j][n-i-1] = 1;
                }
            }
            //右-左
            for (int j = n-2-i; j >=i ; j--) {
                if (visited[m-1-i][j]==0){
                    ans.add(matrix[m-1-i][j]);
                    visited[m-1-i][j] = 1;
                }
            }
            //下-上
            for (int j = m-i-2; j > i ; j--) {
                if (visited[i][j] == 0){
                    ans.add(matrix[j][i]);
                    visited[j][i] =1;
                }
            }
            
        }
        return ans;
    }
    public boolean canJump(int[] nums){
        int length = nums.length;
        int lastCan = length - 1;
        for (int i = length -1; i >=0 ; i--) {
            if (nums[i]+i >= lastCan){
                lastCan = i;
                if (nums[0] >= lastCan){
                    return true;
                }
            }
        }
        return false;
    }
    public List<Interval> merge(List<Interval> intervals) {
        LinkedList<Interval> ret = new LinkedList<>();
        Collections.sort(intervals,(a,b)->(a.start - b.start));
        for (Interval interval : intervals) {
            if (ret.isEmpty() || ret.peekLast().end < interval.start){
                ret.add(interval);
            }else {
                ret.peekLast().end = Math.max(ret.peekLast().end, interval.end);
            }
        }
        return  ret;
    }
    public List<Interval> gerge2(List<Interval> intervals){
        List<Interval> list = new ArrayList<>();
        Interval L = new Interval();
        Interval R = new Interval();
        for (int i = 0; i < intervals.size(); i++) {
            L = intervals.get(i);
            int j = i+1;
            for (; j < intervals.size(); j++) {
                R = intervals.get(j);
                if (R.start == L.start){
                    intervals.set(j,new Interval(L.start,Math.max(L.end,R.end)));
                    break;
                }else if (L.start < R.start){
                    if (L.end >= R.start){
                        Interval interval = new Interval(L.start,Math.max(L.end,R.end));
                        intervals.set(j,interval);
                        break;
                    }
                }else {
                    if (R.end >= L.start){
                        Interval interval = new Interval(R.start,Math.max(L.end,R.end));
                        intervals.set(j,interval);
                        break;
                    }
                }
            }
            if (j>= intervals.size()){
                list.add(intervals.get(i));
            }
        }
        return list;
    }
    public int lengthOfLastWord(String s) {
        int len = s.length();
        char c[] = s.toCharArray();
        int result = 0;
        for (int i = len -1; i >=0 ; i--) {
            if (result == 0 && c[i] == ' '){
                continue;
            }
            if (c[i] !=' '){
                result ++;
            }else {
                return result;
            }
        }
        return result;
    }
    public int[][] generateMatrix(int n) {
        int[][] arr = new int[][]{};
        int c = 1 ,j =0;
        while (c <= n*n){
            for (int i = j;  i< n-j; i++) {
                arr[j][i] = c++;
            }
            for (int i = j+1; i < n-j; i++) {
                arr[i][n-j-1] = c++;
            }
            for (int i = n-j -2; i >= j ; i--) {
                arr[n-j-1][i] = c++;
            }
            for (int i = n-j-2; i > j ; i++) {
                arr[i][j] = c++;
            }
            j++;
        }
        return arr;
    }
    public String getPermutation(int n, int k) {
        
        StringBuilder sb = new StringBuilder();
        //候选数字
        List<Integer> candidates  = new ArrayList<>();
        //分母的阶乘数
        int[] factorials = new int[n+1];
        factorials[0] = 1;
        int fact = 1;
        for (int i = 0; i < n; ++i) {
            candidates.add(i);
            fact *= i;
            factorials[i] =fact;
        }
        
        k -= 1;
        for (int i = n -1; i >0 ; --i) {
            //计算候选数字的index
            int index = k / factorials[i];
            sb.append(candidates.remove(index));
            k -= index*factorials[i];
        }
        return sb.toString();
    }

    public ListNode rotateRight(ListNode head, int k) {
        ListNode cur = new ListNode(0);
        cur.next  = head;
        ListNode fast = head,slow = head;
        int length = 0;
        while (head!=null){
            length ++;
            head = head.next;
        }
        head = cur.next;
        if (k >= length){
            k = k % length;
        }
        while (k > 0){
            fast =fast.next;
            if (fast == null){
                fast = head;
            }
            k --;
        }
        while (fast.next != null){
            slow = slow.next;
            fast = fast.next;
        }
        if (slow.next == null){
            return head;
        }
        ListNode oldTail = fast,tail = slow ,newHead = tail.next;
        tail.next = null;
        oldTail.next = head;
        return newHead;
        
    }
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i==0 || j == 0)
                    dp[i][j] = 1;
                else 
                    dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }
        return dp[m-1][n-1];
    }
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int[][] dp = new int[m][n];
        dp[0][0] = obstacleGrid[0][0] == 1?0:1;
        for (int i = 0; i < m; i++) {
            dp[i][0] = (obstacleGrid[i][0]==1 || dp[i-1][0] ==0)?0:1;
        }
        for (int j = 0; j < n; j++) {
            dp[0][j] = (obstacleGrid[0][j] ==1 || dp[0][j] ==0) ? 0:1;
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (obstacleGrid[i][j]==1){
                    dp[i][j] = 0;
                }else {
                    dp[i][j] = dp[i-1][j] + dp[i][j-1];
                }
            }
        }
        return dp[m-1][n-1];
    }
    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int [][] dp = new int[m][n];
        dp[0][0] = grid[0][0];
        for (int i = 1; i < m; i++) {
            dp[i][0] = grid[i][0]+dp[i-1][0];
        }
        for (int j = 1; j < n; j++) {
            dp[0][j] = grid[0][j] + dp[0][j-1];
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                grid[i][j] = Math.min(dp[i-1][j],dp[i][j-1])+grid[i][j];
            }
        }
        return dp[m-1][n-1];
    }
    public int[] plusOne(int[] digits) {
        boolean flag = false;
        digits[digits.length-1] +=1;
        for (int i = digits.length-1; i >=0 ; i--) {
            if (digits[i] < 10){
                return digits;
            }else {
                digits[i] = 0;
                if (i != 0){
                    digits[i-1] +=1;
                }
            }
        }
        int[] res = new int[digits.length +1];
        res[0] =1;
        System.arraycopy(digits,0,res,1,digits.length);
        return res;
    }

    public String addBinary(String a, String b) {
        StringBuilder sb = new StringBuilder();
        int alen = a.length() -1;
        int blen= b.length() - 1;
        int temp = 0;
        while (alen >=0 || blen >=0){
            int sum = temp;
            if (alen >=0){
                sum = sum +(a.charAt(alen--)- '0');
            }
            if (blen >=0){
                sum = sum + (b.charAt(blen--) -'0');
            }
            sb.append(sum %2);
            temp = sum /2;
        }
        if (temp !=0){
            sb.append(temp);
        }
        return sb.reverse().toString();
    }
    public int mySqrt(int x) {
        int result = 0;
        for (int i = 0; i <= x/i; i++) {
            if (i*i == x){
                return i;
            }
            result = i;
        }
        return result;
    }
    //dp
    public int climbStairs(int n) {
        if (n <=2){
            return n;
        }
        int tmp = 1;
        int copy ;
        int res = 2 ;
        for (int i = 3; i <=n ; i++) {
            copy = res;
            res += tmp;
            tmp = copy;
        }
        return res;
    }
    public String simplifyPath(String path) {
        String[] words = path.split("/");
        Stack<String> s = new Stack<>();
        for (String word : words) {
            if ("".equals(word) || ".".equals(word) || ("..".equals(word) && s.isEmpty())){
                continue;
            }else if ("..".equals(word) && !s.isEmpty()){
                s.pop();
            }else {
                s.push(word);
            }
        }
        StringBuilder res = new StringBuilder();
        if (s.isEmpty()){
            return "/";
        }
        if (!s.isEmpty()){
            Object[] a = s.toArray();
            for (int i = 0; i < a.length; i++) {
                res.append("/" + a[i].toString());
            }
        }
        return res.toString();
    }
    public void setZeroes(int[][] matrix) {
        if (matrix == null || matrix.length <= 0){
            return;
        }
        int m = matrix.length;
        int n = matrix[0].length;
        boolean[] row = new boolean[m];
        boolean[] col = new boolean[n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j <n ; j++) {
                if (matrix[i][j] == 0){
                    row[i] = true;
                    row[j] = true;
                }
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n ; j++) {
                if (row[i] && col[j]){
                    for (int k = 0; k < m; k++) {
                        matrix[k][j] = 0;
                    }
                    for (int k = 0; k < n; k++) {
                        matrix[i][k] = 0;
                    }
                }
            }
        }
    }
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length ==0)
            return false;
        int m = 0;
        int n = matrix[0].length -1;
        while (m < matrix.length && n >=0){
            if (matrix[n][m] < target){
                m ++;
            }else if (matrix[m][n] > target){
                n--;
            }else 
                return true;
        }
        return false;
    }
    public boolean searchMatrix2(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0) 
            return false;
        int m = matrix.length,n = matrix[0].length;
        int low = 1,high = m* n, i =0,j=0;
        for (int mid  = (low+high) /2; (mid >= low ) && (mid <= high) ; mid =(low+high) /2) {
            i = (mid -1) /n ; j = (mid -1) %n;
            if (matrix[i][j] == target) return true;
            if (matrix[i][j] > target){
                high = mid-1;
            }else {
                low = mid +1;
            }
        }
        StringBuilder sb  = new StringBuilder();
        StringBuffer stringBuffer = new StringBuffer();
        return false;
    }
    public void sortColors(int[] nums) {
//        int length = nums.length;
//        for (int i = 0; i < length; i++) {
//            for (int j = i+1; j < length; j++) {
//                if (nums[i]>nums[j]){
//                    int temp = nums[i];
//                    nums[i] = nums[j];
//                    nums[j] = temp;
//                }
//            }
//        }
        int v = 1;
        int lt = -1;
        int gt = nums.length;
        int i = 0;
        while (i < gt){
            if (nums[i] < v){
                swap(nums,i++,++lt);
            }else if (nums[i] > v){
                swap(nums,i,--gt);
            }else {
                i++;
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> lists = new ArrayList<>();
        if (n< k || k<=0){
            return lists;
        }
        
        List<Integer> list = new ArrayList<>();
        combineFork(lists,list,n,k,1);
        return lists;
    }

    private void combineFork(List<List<Integer>> lists, List<Integer> list, int n, int k, int start) {
        if (list.size() == k){
            lists.add(new ArrayList<>(list));
            return;
        }
        for (int i = start; i <= (n-(k - list.size())) ; i++) {
            list.add(i);
            combineFork(lists,list,n,k,i+1);
            list.remove(list.size()-1);
        }
    }
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> lists = new ArrayList<>();
        subsetsDFS(nums,lists,new ArrayList<>(),0);
        return lists;
    }

    private void subsetsDFS(int[] nums, List<List<Integer>> lists, ArrayList<Integer> objects, int index) {
        lists.add(new ArrayList<Integer>(objects));
        for (int i = index; i < nums.length ; i++) {
            objects.add(nums[i]);
            subsetsDFS(nums,lists,objects,index+1);
            objects.remove(objects.size() -1);
        }
    }
    public boolean exist(char[][] board, String word) {
        if (board == null || board.length==0 || word ==null){
            return false;
        }
        int m  = board.length;
        int n = board[0].length;
        boolean[][] flag = new boolean[m][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (existDFS(board,m,n,flag,word,0)){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean existDFS(char[][] board, int m, int n, boolean[][] flag, String word, int start) {
        if (m < 0 || m>board.length || n < 0 || n>=board[0].length || board[m][n] !=word.charAt(start) || flag[m][n] ==true)
            return false;
        if (start == word.length()){
            return true;
        }
        flag[m][n] = true;
        if (existDFS(board,m-1,n,flag,word,start+1) 
              || existDFS(board,m+1,n,flag,word,start+1)
              || existDFS(board,m,n+1,flag,word,start+1)
              || existDFS(board,m,n-1,flag,word,start+1)){
            return true;
        }
        flag[m][n] = false;
        return false;
        
    }
    
    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        int[][] dp = new int[m+1][n+1];
        for (int i = 0; i < m; i++) {
            dp[i][0] = i;
        }
        for (int i = 0; i < n; i++) {
            dp[0][i] = i;
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i-1) == word2.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1];
                }else {
                    dp[i][j] = Math.min(dp[i-1][j-1],Math.min(dp[i-1][j],dp[i][j-1])) +1;
                }
            }
        }
        return dp[m][n];
    }
    public String minWindow(String s, String t) {
        char[] or = s.toCharArray();
        char[] tt = t.toCharArray();
        int m = s.length();
        int n = t.length();
        return  "";
    }
    public int removeDuplicates(int[] nums) {
        int i = 0;
        for (int n : nums)
            if (i < 2 || n > nums[i-2])
                nums[i++] = n;
        return i;
    }
    public ListNode deleteDuplicates(ListNode head) {
//        ListNode p = head;
//        while (p != null && p.next != null){
//            if (p.val == p.next.val){
//                p.next = p.next.next;
//            }else {
//                p = p.next;
//            }
//            
//        }
//        return  head;
        if (head == null || head.next == null){
            return head;
        }
        head.next = deleteDuplicates(head.next);
        if (head.val == head.next.val){
            head = head.next;
        }
        return head;
    }
    public ListNode deleteDuplicates2(ListNode head) {
        if (head == null || head.next == null){
            return head;
        }
        //虚拟头结点 + 双指针
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode pre = dummy;
        ListNode cur = dummy.next;
        while (pre != null && cur !=null & cur.next!=null){
            if (cur.val == cur.next.val){
                while (cur != null && cur.next!=null && cur.val == cur.next.val){
                    cur = cur.next;
                }
                pre.next = cur.next;
                cur = pre.next;
            }else {
                pre = pre.next;
                cur = cur.next; 
            }
        }
        return dummy.next;
    }
    public int largestRectangleArea(int[] heights) {
        if (heights == null || heights.length == 0){
            return 0;
        }
        int max = Integer.MIN_VALUE;
        Stack<Integer> stack = new Stack<>();
        int n = heights.length;
        for (int i = 0; i < n; i++) {
            int curHeight = i == n ? -1:heights[i];
            while (!stack.isEmpty() && curHeight <= heights[stack.peek()]){
                int stackHeight = heights[stack.pop()];
                int width = stack.isEmpty() ? i : i -stack.peek() -1;
                max = Math.max(max, stackHeight * width);
            }
        }
        return max;
    }
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m-1,j=n,index = m+n+1;
        while (i >= 0 || j >=0){
            if (i<0){
                nums1[index--] = nums2[j--];
            }else if (j<0){
                break;
            }
            if (nums1[i]>=nums2[j]){
                nums1[index--] =nums1[i--];
            }else {
                nums1[index--] = nums2[j--];
            }
        }
    }
    public int numDecodings(String s) {
        if (s == null || s.isEmpty()){
            return 0;
        }
        int[] dp = new int[s.length()];
        if (s.charAt(0) == '0'){
            return 0;
        }
        dp[0] = 1;
        for (int i = 0; i < s.length() ; i++) {
            if (s.charAt(i) !='0'){
                dp[i] = dp[i-1];
            }
            int temp = (s.charAt(i-1) - '0')*10 + (s.charAt(i)-'0');
            if (temp >= 10 && temp <= 26){
                dp[i] += (i >=2 ? dp[i-2]:1);
            }
        }
        return dp[s.length()-1];
    }
    public List<Integer> inorderTraversal(TreeNode root) {
//        List<Integer> list = new ArrayList<>();
//        Stack<TreeNode> stack = new Stack<>();
//        while (root != null || !stack.isEmpty()){
//            while (root != null){
//                stack.push(root);
//                root = root.left;
//            }
//            root = stack.pop();
//            list.add(root.val);
//            root = root.right;
//        }
//        return list;
        //递归
        List<Integer> list = new ArrayList<>();
        inorderTravesalHFS(root,list);
        return list;
    }

    private void inorderTravesalHFS(TreeNode root, List<Integer> list) {
        if (root !=null){
            inorderTravesalHFS(root.left,list);
            list.add(root.val);
            inorderTravesalHFS(root.right,list);
        }
    }
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p==null && q == null){
            return true;
        }
        if (p == null && q != null || p!=null && q == null){
            return false;
        }
        return p.val == q.val && isSameTree(p.left,q.left) && isSameTree(p.right,q.right);
    }
    public int largestRectangleArea2(int[] heights) {
        if (heights == null || heights.length == 0){
            return 0;
        }
        int max = Integer.MIN_VALUE;
        Stack<Integer> stack = new Stack<>();
        int n = heights.length;
        for (int i = 0; i < n; i++) {
            int curHeight = i == n ? -1 : heights[i];
            while (stack.isEmpty() && curHeight<= heights[stack.peek()]){
                int stackHeight = heights[stack.pop()];
                int width = stack.isEmpty() ? i : i -stack.peek() -1;
                max = Math.max(max,stackHeight * width);
            }
            stack.push(i);
        }
        return max;
    }
    public int maxDepth(TreeNode root){
        return root == null ? 0 : Math.max(maxDepth(root.left),maxDepth(root.right) + 1);
    }
    //利用队列先进先出 实现二叉树层次遍历
    public List<List<Integer>> levelOrder(TreeNode root){
        if (root == null){
            return  new ArrayList<>();
        }
        List<List<Integer>> res = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()){
            int count = queue.size();
            List<Integer> list = new ArrayList<>();
            while (count > 0){
                TreeNode node = queue.poll();
                list.add(node.val);
                if (node.left != null){
                    queue.add(node.left);
                }
                if (node.right != null){
                    queue.add(node.right);
                }
                count --;
            }
            res.add(list);
        }
        return res;
    }
    public TreeNode sortedArrayToBST(int[] nums) {
        return sortedArrayToBST(nums,nums.length-1,0);
    }

    private TreeNode sortedArrayToBST(int[] nums, int hi, int low) {
        if (low<=hi){
            int mid = low + (hi - low)/2;
            TreeNode root = new TreeNode(nums[mid]);
            root.left = sortedArrayToBST(nums,mid -1 ,low);
            root.right = sortedArrayToBST( nums,hi,mid +1);
            return root;
        }else {
            return null;
        }
    }
    public int minDepth(TreeNode root) {
        if (root == null){
            return 0;
        }
        if (root.left == null) return 1+ minDepth(root.right);
        if (root.right == null) return 1+ minDepth(root.left);
        int ld = 1 + minDepth(root.left);
        int rd = 1 + minDepth(root.right);
        return  Math.min(ld,rd);
    }
    public boolean hasPathSum(TreeNode root, int sum) {
        if (root == null) {
            return false;
        }
        if (root.left == null && root.right == null){
            return sum - root.val == 0;
        }
        return hasPathSum(root.left,sum - root.val) || hasPathSum(root.right,sum - root.val);
    }
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> lists = new ArrayList<>();
        if (numRows<1){
            return lists;
        }
        lists.add(Arrays.asList(1));
        for (int i = 1; i < numRows ; i++) {
            List<Integer> list = new ArrayList<>();
            List<Integer> pre = lists.get(i-1);
            for (int j = 0; j <=i ; j++) {
                if (j==0 || j==i){
                    list.add(1);
                }else {
                    list.add(pre.get(j - 1) + pre.get(j));
                }
            }
            lists.add(list);
        }
        return lists;
    }
    public List<Integer> getRow(int rowIndex) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        for (int i = 0; i <= rowIndex ; i++) {
            list.add(1);
            for (int j = i-1; j >=1 ; j--) {
                list.set(j,list.get(j) + list.get(j-1));
            }
        }
        return list;
    }
    public int minimumTotal(List<List<Integer>> triangle) {
        if (triangle == null || triangle.size() == 0){
             return 0;
        }
        //加1 可以不用初始化最后一层
        int[][] dp = new int[triangle.size() +1][triangle.size() +1];
        for (int i = triangle.size() -1; i >= 0; i--) {
            List<Integer> curTr = triangle.get(i);
            for (int j = 0; j < curTr.size() ; j++) {
                dp[i][j] = Math.min(dp[i+1][j],dp[i+1][j+1] + curTr.get(j));
            }
        }
        return dp[0][0];
    }
    public int maxProfit(int prices[]){
        int minprice = Integer.MAX_VALUE;
        int maxprofit =0;
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < minprice){
                minprice = prices[i];
            }else if (prices[i] - minprice > maxprofit){
                maxprofit = prices[i] - minprice;
            }
        }
        return maxprofit;
    }
    public int maxProfit2(int[] prices){
        if (prices.length <= 1){
            return 0;
        }
        int min = prices[0];
        int profit = prices[1] - prices[0];
        for (int i = 2; i < prices.length ; i++) {
            min = Math.min(prices[i -1],min);
            profit  = Math.max(profit,prices[i] - min);
        }
        if (profit < 0){
            return  0;
        }
        return profit;
    }
    public int maxProfit11(int[] prices) {
        if (prices.length <= 1){
            return 0;
        }
        int maxprofit = 0;
        for (int i = 0; i < prices.length ; i++) {
            if (prices[i] > prices[i-1]){
                maxprofit += prices[i] - prices[i-1];
            }
        }
        return maxprofit;
    }
    public boolean isPalindrome(String s) {
        int left = 0;
        int right = s.length()-1;
        s = s.toLowerCase();
        while (left < right){
            char l = s.charAt(left);
            while (left<right &&  l != ' '){
                left++;
                l = s.charAt(left);
            }
            char r = s.charAt(right);
            while (left<right && r != ' '){
                right--;
                r = s.charAt(right);
            }
            if (r != l){
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
    public void solve(char[][] board) {
        if (board == null || board.length ==0){
            return;
        }
        int row = board.length;
        int col = board[0].length;
        for (int i = 0; i < row; i++) {
            dfs(board,i,0);
            dfs(board,i,col-1);
        }
        for (int i = 0; i < col; i++) {
            dfs(board,0,i);
            dfs(board,row-1,i);
        }
        
    }

    private void dfs(char[][] board, int i, int j) {
        if (i<0 || j<0 || i>=board.length || j>= board[0].length || board[i][j]!='O' ){
            return;
        }
        board[i][j] = '-';
        dfs(board,i-1,j);
        dfs(board,i+1,j);
        dfs(board,i,j-1);
        dfs(board,i,j+1);
        return;
    }
    public List<List<String>> partition(String s) {
        List<List<String>> result = new ArrayList<>();
        List<String> sub = new ArrayList<>();
        partitiondfs(s,0,sub,result);
        return result;
    }

    private void partitiondfs(String s, int index, List<String> sub, List<List<String>> result) {
        if (index == s.length()){
            result.add(new ArrayList<>(sub));
            return;
        }
        String prefix = "";
        for (int i = index; i <s.length() ; i++) {
            prefix += s.charAt(i);
            if (!isPalindrome(prefix)){
                continue;
            }
            sub.add(prefix);
            partitiondfs(s,i+1,sub,result);
            sub.remove(sub.size() -1 );
        }
        
    }
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int rest = 0, run = 9, start = 0;
        for (int i = 0; i < gas.length; i++) {
            run += gas[i] - cost[i];
            rest += gas[i] - cost[i];
            if (run < 0){
                start = i +1;
                run = 0;
            }
        }
        return rest<0?-1:start;
    }
    public int singleNumber(int[] nums) {
        int len = nums.length;
        int result = 0;
        for (int i = 0; i < len ; i++) {
            result ^= nums[i];
        }
        return result;
    }
    public int candy(int[] ratings){
        int pre = 1;
        int countDown = 0;
        int total = 1;
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] >= ratings[i-1]){
                if (countDown > 0){
                    total += countDown * (countDown +1 ) /2 ;
                    if (countDown >= pre){
                        total  += countDown -  pre +1;
                    }
                    pre =1;
                    countDown = 0;
                }
                pre = ratings[i] == ratings[i-1] ? 1 : pre +1;
                total += pre;
            }else {
                countDown++;   
            }
        }
        if (countDown > 0){
            total += countDown * (countDown +1) /2 ;
            if (countDown >= pre){
                total += countDown - pre +1;
            }
        }
        return  total;
    }
    public boolean wordBreak(String s, List<String> wordDict) {
        //
        int n = s.length();
        boolean[] memo = new boolean[n+1];
        memo[0] = true;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (memo[j] && wordDict.contains(s.substring(j,i))){
                    memo[i] = true;
                    break;
                }
            }
        }
        return memo[n];
    }
    public List<Integer> preorderTraversal(TreeNode root) {
        Stack<TreeNode> st = new Stack<>();
        List<Integer> list = new ArrayList<>();
        while (root!=null || !st.empty()){
            if (root!=null){
                list.add(root.val);
                st.push(root);
                root = root.left;
            }else {
                root = st.pop().right;
            }
        }
        return list;
    }
    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0,ans; i < tokens.length; i++) {
            String s = tokens[i];
            if (s.equals("+")){
                ans = stack.pop() + stack.pop();
                stack.push(ans);
            }else if (s.equals("-")){
                ans = -stack.pop() + stack.pop();
                stack.push(ans);
            }else if (s.equals("*")){
                ans = stack.pop() * stack.pop();
                stack.push(ans);
            }else if (s.equals("/")){
                int second = stack.pop(),first = stack.pop();
                ans = first / second;
                stack.push(ans);
            }else {
                stack.push(Integer.parseInt(s));
            }
        }
        return stack.pop();
    }
    public String reverseWords(String s){
        String[] strings = s.split(" ");
        if (strings.length == 0){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(strings[strings.length-1]);
        for (int i = strings.length -2; i >=0 ; i--) {
            if (!strings[i].equals("")){
                sb.append("").append(strings[i]);
            }
        }
       return sb.toString();
    }
    public int maxProduct(int[] nums) {
        if (nums.length == 1){
            return 0;
        }
        int max =nums[0];
        int[] dp = new int[nums.length];
        int[] dp2 = new int[nums.length];
        
        for (int i = 0; i < nums.length ; i++) {
            if (nums[i] >0){
                dp[i] = Math.max(nums[i],dp[i-1]*nums[i]);
                dp2[i] = Math.min(nums[i],dp2[i-1] * nums[i]);
            }else {
                dp[i] = Math.max(nums[i],dp2[i-1]*nums[i]);
                dp2[i] = Math.min(nums[i],dp[i-1] * nums[i]);
            }
            max = Math.max(max,Math.max(dp[i], dp2[i]));
                     
        }
        return max;
    }
    public int findMin(int[] nums) {
        int l = 0, r = nums.length -1,m;
        while (l<r){
            m = (l+r) >>>1;
            if (nums[m] >= nums[l]){
                if (nums[m] > nums[r]){
                    l = m+1;
                }else {
                    r = m;
                }
            }else {
                r = m;
            }
        }
        return nums[l];
    }
    public int findMin2(int[] nums){
        int l = 0,r = nums.length -1,mid ;
        while (l < r){
            mid = (r + l ) /2;
            if (nums[mid] < nums [r]){
                r = mid;
            }else if (nums[mid] > nums[r]){
                l = mid+1;
            }else {
                r--;
            }
        }
        return nums[l];
    }
    public int findPeakElement(int[] nums) {
        //找到峰值
        //左右都是负无穷
        //二分法
        int left = 0;
        int right = nums.length -1 ;
        int mid = 0;
        while (left < right){
            mid = left + (right -left)/2;
            if (mid == 0 && nums[mid] > nums[mid+1] ||
                mid == nums.length - 1 && nums[mid] > nums[mid-1] ||
                nums[mid] > nums[mid+1] && nums[mid] > nums[mid -1]){
                return mid;        
            }      
            if (nums[mid] > nums[mid +1]){
                right  =mid;
            }else {
                left = mid +1;
            }
            
        }
        return mid;
    }
    public static void main(String[] args) {
//        createNodeList();
//        System.out.println(intToRoman(6));    
//        System.out.println(romanToInt2("MCMXCIV"));
//        System.out.println(threeSum(new int[]{-1, 0, 1, 2, -1, -4})); 
//        System.out.println(letterCombinations("23"));  
//    
//    }    ListNode l1 = new ListNode(1);
//        ListNode head = l1;
//        l1.next = new ListNode(2);
//        l1 = l1.next;
//        l1.next = new ListNode(3);
//        l1 = l1.next;
//        l1.next = new ListNode(4);
//        ListNode aa =removeNthFromEnd(head,1);
//        System.out.println(aa);
//        System.out.println(generateParenthesis2(3));
        // nums 是以“引用”方式传递的。也就是说，不对实参做任何拷贝
//        int[] nums = new int[]{0,0,1,1,1,2,2,3,3,4};
//        int len = removeDuplicates(nums);
//
//    // 在函数里修改输入数组对于调用者是可见的。
//    // 根据你的函数返回的长度, 它会打印出数组中该长度范围内的所有元素。
//        for (int i = 0; i < len; i++) {
//            System.out.println((nums[i]));
//        }
//        System.out.println(strStr("aaaaaa","abb"));
//        "sss".indexOf("s");
        System.out.println(maxSubArray(new int[]{-2,1,-3,4,-1,2,1,-5,4}));
    }

    

}
 class Interval {
      int start;
      int end;
      Interval() { start = 0; end = 0; }
      Interval(int s, int e) { start = s; end = e; }
}

class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
     TreeNode(int x) { val = x; }
}
