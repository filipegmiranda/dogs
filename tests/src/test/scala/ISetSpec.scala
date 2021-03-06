package dogs
package tests

import cats.laws.discipline.SerializableTests
import cats._
import cats.tests.CatsSuite
import cats.implicits._

class ISetSpec extends CatsSuite {
 
  checkAll("MonoidK[ISet]", SerializableTests.serializable(MonoidK[ISet]))

  test("intersection works")(
    forAll { (as: List[Int], bs: List[Int]) =>

      val setA: ISet[Int] = Set.fromList(as).iset
      val setEven: ISet[Int] = ISet(_ % 2 == 0)

      val s1 = setA & setEven
      val s2 = setA intersection setEven

      bs.forall(b => (s1(b) == (as.contains(b) && (b % 2 == 0))) &&
                  (s2(b) == (as.contains(b) && (b % 2 == 0)))) should be(true)

    })

  test("union works")(
    forAll { (as: List[Int], bs: List[Int]) =>
      val setA: ISet[Int] = Set.fromList(as).iset
      val setEven: ISet[Int] = ISet(_ % 2 == 0)

      val s1 = setA | setEven
      val s2 = setA union setEven

      bs.forall(b => (s1(b) == (as.contains(b) || (b % 2 == 0))) &&
                  (s2(b) == (as.contains(b) || (b % 2 == 0)))) should be (true)

    })

  test("difference works") (
    forAll { (as: List[Int], bs: List[Int]) =>
      val setA: ISet[Int] = Set.fromList(as).iset
      val setEven: ISet[Int] = ISet(_ % 2 == 0)

      val s1 = setA - setEven
      val s2 = setA diff setEven

      bs.forall(b => (s1(b) == (as.contains(b) && (b % 2 != 0))) &&
                  (s2(b) == (as.contains(b) && (b % 2 != 0)))) should be (true)

    })

  test("negation works")(
    forAll { (as: List[Int], bs: List[Int]) =>
      val setA: ISet[Int] = Set.fromList(as).iset
      val setEven: ISet[Int] = ISet(_ % 2 == 0)

      val s1 = !(setA - setEven)

      bs.forall(b => (s1(b) != (as.contains(b) && (b % 2 != 0)))) should be(true)

    })
}
