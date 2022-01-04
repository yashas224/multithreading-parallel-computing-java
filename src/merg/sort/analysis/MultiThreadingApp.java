package merg.sort.analysis;

import java.util.Scanner;

public class MultiThreadingApp {

	public static void main(String[] args) throws InterruptedException {
		int arr[] = new int[101];
		for (int i = 100; i >= 0; i--) {
			arr[100 - i] = i;
		}
		ParallelMergeSort obj = new ParallelMergeSort(arr);
		System.out.println("Before Sorting");
		obj.display();
		System.out.println();
		System.out.println("After sorting");
		System.out.println();
		obj.parallelMergSort(0, arr.length - 1, arr, 6);
		System.out.println();
		obj.display();
	}

}

class ParallelMergeSort {
	int nums[];

	public ParallelMergeSort(int[] nums) {
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

	public void parallelMergSort(int i, int j, int[] nums, int nosOfThreads) throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + " starting  for :" + i + " to " + j);

		if (nosOfThreads == 1) {
			divide(i, j, nums);
			System.out.println(Thread.currentThread().getName() + " merging from :" + i + " to " + j);
			return;
		}

		int m = (i + j) / 2;
		Thread leftThread = createThreads(i, m , nums, nosOfThreads);
		Thread rightThread = createThreads(m + 1, j, nums, nosOfThreads);
		leftThread.start();
		rightThread.start();

		leftThread.join();
		rightThread.join();

		System.out.println(Thread.currentThread().getName() + " merging from :" + i + " to " + j);
		mergeIntervals(i, j, nums, m);
	}

	private Thread createThreads(int i, int j, int[] nums, int nosOfThreads) {
		return new Thread(() -> {
			System.out
					.println("Thread details " + Thread.currentThread().getName() + " working form :" + i + " to " + j);
			try {
				this.parallelMergSort(i, j, nums, nosOfThreads / 2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
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