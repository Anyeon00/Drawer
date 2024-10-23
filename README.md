## 1. 객체 지향적으로 다양한 도형 추가

  ![스크린샷 2024-10-22 145650](https://github.com/user-attachments/assets/40d6f4ad-af0f-41b6-99f5-3924b22ad240)

  draw함수로 그릴 때, 필요한 x,y가 
- 1개면 OnePointFigure를 상속
  ```java
  ex)
  g.drawOval(x1 - GAP, y1 - GAP, 2 * GAP, 2 * GAP);
  ```
- 2개면 TwoPointFigure를 상속
  ```java
  ex)
  int minX = Math.min(x1, x2);
  int minY = Math.min(y1, y2);
  int width = Math.abs(x2 - x1);
  int height = Math.abs(y2 - y1);
  g.drawRect(minX, minY, width, height);
  ```

<br>

## 2. MVC패턴으로 TableDialog와 TreeDialog 추가

### Table Dialog
 ![스크린샷 2024-10-22 145727](https://github.com/user-attachments/assets/035a21d6-f29b-42ba-bb09-43b7c20cf28f)

### Tree Dialog
 ![스크린샷 2024-10-22 145758](https://github.com/user-attachments/assets/42a20ad2-f978-4908-92a1-f384f6cb3581)

<br>

## 3. Serialize기술로 저장 및 출력

### 다른 이름으로 저장하기
  ![스크린샷 2024-10-22 145831](https://github.com/user-attachments/assets/40acafb1-b9d2-4725-8c4e-9dcd17683a1c)

  ![스크린샷 2024-10-24 002711](https://github.com/user-attachments/assets/31042aa8-e72d-4163-a2d7-1a24d99b4cb1)

