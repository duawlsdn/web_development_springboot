import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
// import static 의 경우 종종 보게 될텐데 정적 메서드에 해당하는 것을 가져 올때 사용

public class JUnitQuiz {
    /*
        문제 01.
        String 으로 선언한 변수 3개가 있습니다. 여기서 세 변수 모두 null이 아니면 name1 과 name2 는 같은 값을 가지고,
        name3 는 다른 나머지 두 변수와 다른 값을 가지는데, 이를 검증하는 테스트를 작성
     */

    @Test
    public void junitTest1(){
        String name1 = "홍길동";
        String name2 = "홍길동";
        String name3 = "홍길금";

        // 모든 변수가 null 이 아닌 지 확인하는 코드
        assertThat(name1).isNotNull();
        assertThat(name2).isNotNull();
        assertThat(name3).isNotNull();

        // name1 과 name2가 같은지 확인
        assertThat(name1).isEqualTo(name2);

        // name1 과 name3가 다른지 확인
        assertThat(name1).isNotEqualTo(name3);

    }
    @Test
    public void junitTest2(){
        /*
            문제 02.
                int 로 선언된 변수 3개가 있습니다 . number1,2,3 는 각각 15,0,-5
                세 변수가 각각 양수, 0, 음수,
                number 1은 2 qhek zmsrkqt
                3 는 2 보다 작은 값임을 검증하는 테스
         */
        int number1 = 15;
        int number2 = 0;
        int number3 = -5;

        // 세 변수가 각각 양수, 0, 음수인지 확인
        assertThat(number1).isPositive();
        assertThat(number2).isZero();   // 양수인지 확인하면 false / 음수인지 확인해도 false
        assertThat(number3).isNegative();

        // number1 이 number2 보다 큰 값인지
        assertThat(number1).isGreaterThan(number2);

        // number3 이 number2 보다 작은 값인지
        assertThat(number3).isLessThan(number2);

    }
}
