package def;

public class BinarySearchSpan {

	// Цикл
	// pred: (a - монотонно убывающий)
	public static int binarySearchLeft(int x, int[] a) {
		int l = -1;
		int r = a.length;
		// inv: ((l == -1) || !(a[l] <= x)) && ((r == a.length) || (a[r] <= x))
		while (r - l > 1) {
			int m = (r - l) / 2 + l;
			if (a[m] <= x) {
				r = m;
			} else {
				l = m;
			}
		}
		return r;
	}

	// post: R = min(i : a[i] < x)

	// Рекурсия
	// pred: !(a[l] <= x) && (a[r] <= x) && (l >= -1) && (r <= a.length) && (a -
	// монотонно убывающий)
	private static int binarySearchRightStep(int x, int[] a, int l, int r) {
		if (r - l == 1) {
			return r;
		}
		int m = (r - l) / 2 + l;
		if (a[m] < x) {
			return binarySearchRightStep(x, a, l, m);
		} else {
			return binarySearchRightStep(x, a, m, r);
		}
	}

	// post: R = min(i : a[i] <= x)

	// pred: (a - монотонно убывающий)
	public static int binarySearchRight(int x, int[] a) {
		return binarySearchRightStep(x, a, -1, a.length);
	}

	// post: R = min(i : a[i] <= x)

	public static void main(String[] args) {
		int x = Integer.parseInt(args[0]);
		int[] a = new int[args.length - 1];
		for (int i = 1; i < args.length; i++) {
			a[i - 1] = Integer.parseInt(args[i]);
		}
		int place = binarySearchLeft(x, a);
		int count = binarySearchRight(x, a) - place;
		String ans = String.valueOf(place) + ' ' + String.valueOf(count);
		System.out.print(ans);
	}

}
