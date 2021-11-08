# Allsinsa

## 프로젝트 개요
> 한국 대표 의류 이커머스 플랫폼 무신사 스토어 백엔드 클론코딩

### 목적
- 이커머스 도메인의 비즈니스 로직 고민
- SpringBoot & JPA 를 바탕으로, Rest API 작성 및 테스트코드 작성 능력 함양

### 팀 구성 및 역할

- 박정미 **ProductOwner**
- 김현준 **ScrumMaster**
- 정은우 **Developer**
- 전효희 **Developer**

### 사용 기술
**백엔드**
- Java17, SpringBoot, Spring Data JPA, H2, gradle, JUnit5

**협업**

- Git, Jira, Slack

- [Git 브랜치 관리 전략](https://techblog.woowahan.com/2553/)
- 위 링크를 참고하여, 아래와 같은 형태로 브랜치를 구성하여 관리합니다.
  - **Main**: 배포시 사용, 아직 배포단계에 이르지 않아 Main 브랜치에 내용이 없습니다.
  - **Develop**: 완전히 개발이 끝난 부분에 대해서만 Merge를 진행합니다.
  - **Feature**: 기능 개발을 진행할 때 사용합니다.


## Project Specification
### ERD

![Allsinsa (1)](https://user-images.githubusercontent.com/41041688/140487461-a1654531-9ea8-4d5e-8e4e-c21d941b9096.png)

### Features

- 회원관리
- 상품 및 옵션 등록
- 장바구니
- 쿠폰생성 및 발급
- 주문 기능

### API Document

- [Product API](https://github.com/prgrms-be-devcourse/BEDV1_Allsinsa/blob/develop/src/docs/asciidoc/product-option.pdf)

- [Cart API](https://github.com/prgrms-be-devcourse/BEDV1_Allsinsa/blob/develop/src/docs/asciidoc/cart.pdf)

- [Coupon API](https://github.com/prgrms-be-devcourse/BEDV1_Allsinsa/blob/develop/src/docs/asciidoc/coupon.pdf)

- [Member API](https://github.com/prgrms-be-devcourse/BEDV1_Allsinsa/blob/develop/src/docs/asciidoc/member.pdf)

- [Order API](https://github.com/prgrms-be-devcourse/BEDV1_Allsinsa/blob/develop/src/docs/asciidoc/order.pdf)



## 회고

### **박정미**

- **Linked**

  - 최종 프로젝트 전에 실습할 때처럼 기능 1개씩만 구현하는 것이 아니고 현재 운영되는 큰 서비스를 직접 따라 구현하면서 배운 것을 적용해볼 수 있어서 좋았다.

  - Jira 같은 새로운 협업툴을 사용해서 프로젝트를 진행해볼 수 있어서 좋았다.

- **Learned**

  - 유닛, 통합 테스트에 대해 더 깊게 배우고 적용해볼 수 있었다.

- **Laked**

  - 한 이슈당 예상했던 기간보다 구현부터 pr 완료까지의 시간이 훨씬 오래 걸렸다.

  - aop같은 스프링 기술들도 적용해보고 싶었는데 아쉽다. 다 하고 나니 너무 기본 기능만 했나라는 생각이 들었다.

- **Longed for**

  - 초반에 구현해야 할 api 설계를 다같이 하는 시간을 가졌어야 했다.



### **김현준**

- **Linked**
  - 코드리뷰를 통해서, 다른 팀원이 작성한 코드를 보며 배울점을 찾고, 본인의 코드를 다른 사람에게 피드백 받으면서 배우는 점이 많았다.

- **Learned**

  - **테스트코드 작성**에 공을 들였고, 협업하며 서로가 작성한 코드에 대한 신뢰의 척도가 테스트코드라고 느껴졌다.

  - 동료의 코드를 통해 테스트코드 작성하는 여러 방법들에 대해서 알게 되었고, 앞으로 조금 더 가볍고 꼼꼼한 테스트를 위한 방법들에 대해서 학습하겠다.

- **Laked**

  - **설계의 중요성을 다시한번 깨닫게 되었다.**

  - 티켓 분배를 하며, 구체적으로 어느정도 스펙까지 구현해야 할지, 어떤 식으로 구현해야 할지 방향성이 명확하게 정해놓고 가지 않아, 개발 중간중간에 정책에대한 이야기를 하는데 시간이 많이 소요되었다.

**Longed for**

- **프로젝트를 기획 및 설계**하며, **조금 더 구체적으로(Minimum 클래스 다이어그램) 설계**하여야 개발을 진행하며 어떻게 분업을 해야할 지에 대해서 **명확**해 질것 같다.



### **정은우**

**Linked**

- 혼자 할때보다 큰 프로젝트를 할 수 있어서 좋았다. 다양한 도메인을 각자 맡아서 도메인의 크기가 커지고 테이블 속성의 양이 많아지는것을 보며 원하는 기능을 추가하는 것이 재미있었다.

- Git과 Jira 협업 프로그램을 이용하며 소통하는 경험을 쌓으며 편리함을 느낄 수 있었다. 편리함을 느끼는 반면 우리팀이 잘 쓰고 있는지 하는 의문도 생겼고 추가되었으면 하는 아쉬운 기능이 종종 생겼다.

**Learned**

- 팀원 각자 domain 별로 설계를 했는데 나는 중간에서 다른사람이 제작한 domain을 활용해야 했다. 다른 domain을 활용하며 데이터를 가져오는 과정이 힘들다는 것을 깨달았다. 앞으로 설계할때 데이터 넘기는 부분에 대하여 더 대화를 하고 진행해야겠다고 생각했다.

- controller 단에서 데이터를 주는 방법과 받는 방법을 달리 해야하고 간단히 해야한다는 것을 깨달았다. 현재 1.0 버전에서는 request와 response가 똑같아 쓸모없이 주고받는 정보가 많아져 문제가 되는 부분이 많은 것을 테스트 하면서 느꼈다.

**Laked**

- 팀원 다들 분업 프로젝트 설계를 처음 해봐서 설계과정에서 무엇을 해야할지도 모르겠고 오래걸려 힘들었다. 팀원 모두 프로젝트를 경험해보며 이 문제를 앞으로는 자연스럽게 해결할 수 있으리라고 생각한다.

**Longed for**

- 기간이 좀 더 길었으면 더 완성도 있는 프로젝트 결과물을 낼 수 있었지 않았을까 싶다.


### **전효희**

- **Linked**

  - 다른 사람들과 협업 작업을 하며 지라 등의 협업툴을 활용해볼 수 있어서 좋았다.

  - 설계하기 전에 함께 토의하고 고민해보는 과정을 거칠 수 있어서 좋았다.

**Learned**

- 담당할 도메인이 작다면, 업무를 분할하기 어렵다는 것을 알게되었다.

- 가져다 쓰는 데이터여도 스냅샷으로 개별 저장이 필요한 부분이 있음을 알게되었다.

**Laked**

- 시간이 짧다보니 구현할 수 있었던 기능이 한정되어 아쉬웠다.

- 개인적으로 스크럼등에 참여를 많이 못한 것 같아 아쉬웠다.

**Longed for**

- 프론트와 그에 사용될 api를 미리 더 자세히 고려했으면 좋았을 것 같다.