import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cars {
    public static List<int[]> mainCar() {
        List<int[]> car = new ArrayList<>();

        // front wheels (передні колеса)
		car.add(new int[]{5, 16});
		car.add(new int[]{7, 16});

		// body (кузов)
		car.add(new int[]{6, 15});
		car.add(new int[]{6, 16});
		car.add(new int[]{6, 17});

		// rear wheels (задні колеса)
		car.add(new int[]{5, 18});
		car.add(new int[]{7, 18});

        return car;
    }  

    public static List<int[]> leftCar() {
        List<int[]> car = new ArrayList<>();

        // front wheels (передні колеса)
		car.add(new int[]{2, -3});
		car.add(new int[]{4, -3});	

		// body (кузов)
		car.add(new int[]{3, -4});
		car.add(new int[]{3, -3});
		car.add(new int[]{3, -2});

		// rear wheels (задні колеса)
		car.add(new int[]{2, -1});
		car.add(new int[]{4, -1});

        return car;
    } 
	
	
	public static List<int[]> rightCar() {
        List<int[]> car = new ArrayList<>();

		// front wheels (передні колеса)		
		car.add(new int[]{5, -3});
		car.add(new int[]{7, -3});

		// body (кузов)
		car.add(new int[]{6, -4});
		car.add(new int[]{6, -3});
		car.add(new int[]{6, -2});
		
		// rear wheels (задні колеса)
		car.add(new int[]{5, -1});
		car.add(new int[]{7, -1}); 

        return car;
    }  

	public static void moveCar(int turn, List<int[]> mainCar) {
		int[] position = mainCar.get(0);
		
		if ((position[0] + 1 < 7 && turn == 1) || (position[0] - 1 > 0 && turn == -1)) {
			for (int i = 0; i < mainCar.size(); i++) {
				position = mainCar.get(i);
				position[0] += turn;
				mainCar.set(i, position);
			} 
		}		
	}

	public static void produceCars(List<List<int[]>> cars) {
		int strip = new Random().nextInt(2);

		if (strip == 0) {
			cars.add(Cars.leftCar());
		} else if (strip == 1) {
			cars.add(Cars.rightCar());
		}	

		if (cars.isEmpty()) {
			return;
		}

		int[] position = cars.get(0).get(2);
		if (position[1] > 20) cars.remove(0);
	}

	public static void moveCars(List<List<int[]>> cars) {
		int[] position;
		for (List<int[]> i : cars) {
			for (int j = 0; j < i.size(); j++) {
				position = i.get(j);
				position[1]++;
				i.set(j, position);
			} 
		}		
	}

	public static boolean accident(List<int[]> mainCar, List<List<int[]>> cars) {		
		if (cars.isEmpty()) {
			return false;
		}

		int[] firstCar = mainCar.get(0);
		for (List<int[]> car : cars) {
			int[] secondCar = car.get(0);

			if (((firstCar[1] < secondCar[1] + 4) && (firstCar[1] + 4 > secondCar[1]) // side collision (зіткнення збоку)
				&& Math.abs(firstCar[0] - secondCar[0]) == 2) ||
				(firstCar[1] - secondCar[1] == 4 && // rear-end collision (зіткнення заду)
				Math.abs(firstCar[0] - secondCar[0]) <= 2)) { 
				
				return true;		
			}
		}
		
		return false;
	}
} 