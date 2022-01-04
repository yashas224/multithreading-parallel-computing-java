package merg.sort.analysis;

import java.util.Scanner;

public class App {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int size = scanner.nextInt();
		int arr[] = new int[size];
		for (int i = 0; i < size; i++) {
			arr[i] = scanner.nextInt();
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
		divideAndMerge(0, nums.length - 1, nums);
		return nums;
	}

	private void divideAndMerge(int i, int j, int[] nums) {
		if (i < j) {

			int m = (i + j) / 2;
			divideAndMerge(i, m, nums);
			divideAndMerge(m + 1, j, nums);

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

	}

	public void display() {
		for (int i = 0; i < nums.length; i++) {
			System.out.print(nums[i]);
			if (i != nums.length - 1)
				System.out.print(",");
		}
	}

}