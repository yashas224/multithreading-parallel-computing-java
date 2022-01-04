package merg.sort.analysis;

public class App {

	public static void main(String[] args) {
		int arr[] = new int[101];
		for (int i = 100; i >= 0; i--) {
			arr[100 - i] = i;
		}
		MergeSort obj = new MergeSort(arr);
		System.out.println("Before Sorting");
		obj.display();
		System.out.println();
		System.out.println("After sorting");
		obj.sort();
		obj.display();
	}

}

class MergeSort {
	int nums[];

	public MergeSort(int[] nums) {
		this.nums = nums;
	}

	public int[] sort() {
		divide(0, nums.length - 1, nums);
		return nums;
	}

	private void divide(int i, int j, int[] nums) {
		if (i < j) {

			int m = (i + j) / 2;
			divide(i, m, nums);
			divide(m + 1, j, nums);
			mergeIntervals(i, j, nums, m);

		}

	}

	private void mergeIntervals(int i, int j, int[] nums, int m) {
		int[] partA = new int[m - i + 1];
		int[] partB = new int[j - m];
		int k = 0;
		int l = 0;
		for (l = i; l <= m; l++) {
			partA[k++] = nums[l];
		}
		k = 0;
		for (l = m + 1; l <= j; l++) {
			partB[k++] = nums[l];
		}

		k = 0;
		l = 0;
		int start = i;
		while (k < partA.length && l < partB.length) {
			int a = partA[k];
			int b = partB[l];
			if (a < b) {
				nums[start++] = a;
				k++;
			} else {
				nums[start++] = b;
				l++;
			}
		}

		while (k < partA.length) {
			nums[start++] = partA[k++];
		}
		while (l < partB.length) {
			nums[start++] = partB[l++];
		}
	}

	public void display() {
		for (int i = 0; i < nums.length; i++) {
			System.out.print(nums[i]);
			if (i != nums.length - 1)
				System.out.print(",");
		}
	}

}