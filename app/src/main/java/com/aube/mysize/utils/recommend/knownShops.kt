import com.aube.mysize.presentation.model.AgeGroup
import com.aube.mysize.presentation.model.BodyType
import com.aube.mysize.presentation.model.Gender
import com.aube.mysize.presentation.model.PriceRange
import com.aube.mysize.presentation.model.RecommendedShop
import com.aube.mysize.presentation.model.Style

val knownShops = listOf(
    RecommendedShop(
        shopName = "무신사",
        shopUrl = "https://www.musinsa.com",
        styles = listOf(Style.ALL),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES, AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "29CM",
        shopUrl = "https://www.29cm.co.kr",
        styles = listOf(Style.ALL),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES, AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "W Concept",
        shopUrl = "https://www.wconcept.co.kr",
        styles = listOf(Style.ALL),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES, AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "에이블리",
        shopUrl = "https://www.a-bly.com",
        styles = listOf(Style.ALL),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES, AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.LOW,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "지그재그",
        shopUrl = "https://www.zigzag.kr",
        styles = listOf(Style.ALL),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES, AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "브랜디",
        shopUrl = "https://www.brandi.co.kr",
        styles = listOf(Style.ALL),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES, AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.LOW,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "스파오",
        shopUrl = "https://www.spao.com",
        styles = listOf(Style.ALL),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES, AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "유니클로",
        shopUrl = "https://www.uniqlo.com/kr",
        styles = listOf(Style.ALL),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES, AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "나이키",
        shopUrl = "https://www.nike.com/kr",
        styles = listOf(Style.SPORTS, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES, AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "아디다스",
        shopUrl = "https://www.adidas.co.kr",
        styles = listOf(Style.SPORTS, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES, AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "뉴발란스",
        shopUrl = "https://www.nbkorea.com",
        styles = listOf(Style.SPORTS, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "퓨마",
        shopUrl = "https://kr.puma.com",
        styles = listOf(Style.SPORTS),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "MLB",
        shopUrl = "https://www.mlb-korea.com",
        styles = listOf(Style.STREET, Style.SPORTS, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "폴로",
        shopUrl = "https://www.ralphlauren.co.kr",
        styles = listOf(Style.CLASSIC),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.PREMIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "타미 힐피거",
        shopUrl = "https://www.hfashionmall.com/display/brand?brndCtgryNo=BDMA08",
        styles = listOf(Style.CLASSIC),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "자라",
        shopUrl = "https://www.zara.com/kr",
        styles = listOf(Style.MINIMAL, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_TALL, BodyType.AVERAGE_TALL, BodyType.PLUS_TALL)
    ),

    RecommendedShop(
        shopName = "H&M",
        shopUrl = "https://www2.hm.com/ko_kr/index.html",
        styles = listOf(Style.MINIMAL, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_TALL, BodyType.AVERAGE_TALL, BodyType.PLUS_TALL)
    ),

    RecommendedShop(
        shopName = "탑텐",
        shopUrl = "https://topten10.goodwearmall.com",
        styles = listOf(Style.CASUAL, Style.MINIMAL),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
        priceRange = PriceRange.LOW,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "커버낫",
        shopUrl = "https://covernat.net",
        styles = listOf(Style.STREET, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "널디",
        shopUrl = "https://whoisnerdy.com",
        styles = listOf(Style.STREET),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "디스이즈네버댓",
        shopUrl = "https://thisisneverthat.com",
        styles = listOf(Style.STREET),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "오아이오아이컬렉션",
        shopUrl = "https://oioicollection.com",
        styles = listOf(Style.CASUAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_SHORT, BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL,
            BodyType.AVERAGE_SHORT, BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "비바스튜디오",
        shopUrl = "https://vivastudio.co.kr",
        styles = listOf(Style.MINIMAL, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL, BodyType.AVERAGE_AVERAGE,
            BodyType.AVERAGE_TALL, BodyType.PLUS_AVERAGE, BodyType.PLUS_TALL)
    ),

    RecommendedShop(
        shopName = "마르디메크르디",
        shopUrl = "https://mardimercredi.com",
        styles = listOf(Style.ROMANTIC, Style.CLASSIC),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL, BodyType.AVERAGE_AVERAGE,
            BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "더오픈프로덕트",
        shopUrl = "https://theopenproduct.com",
        styles = listOf(Style.CASUAL, Style.STREET),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_TALL, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "LE17SEPTEMBRE",
        shopUrl = "https://le17septembre.com",
        styles = listOf(Style.MINIMAL, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_TALL, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "로씨로씨",
        shopUrl = "https://roccirocci.com",
        styles = listOf(Style.ROMANTIC, Style.VINTAGE),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_SHORT, BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL,
            BodyType.AVERAGE_SHORT, BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "아워코모스",
        shopUrl = "https://ourcomos.com",
        styles = listOf(Style.MINIMAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL, BodyType.AVERAGE_AVERAGE,
            BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "로맨틱크라운",
        shopUrl = "https://www.romanticcrown.com",
        styles = listOf(Style.CASUAL, Style.STREET),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL, BodyType.AVERAGE_AVERAGE,
            BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "비슬로우",
        shopUrl = "https://beslow.co.kr",
        styles = listOf(Style.MINIMAL, Style.CLASSIC),
        ageGroup = listOf(AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.MALE,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL, BodyType.AVERAGE_AVERAGE,
            BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "코스",
        shopUrl = "https://www.cosstores.com",
        styles = listOf(Style.MINIMAL),
        ageGroup = listOf(AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL, BodyType.AVERAGE_AVERAGE,
            BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "더한섬닷컴",
        shopUrl = "https://www.thehandsome.com",
        styles = listOf(Style.CLASSIC, Style.MINIMAL),
        ageGroup = listOf(AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL, BodyType.AVERAGE_AVERAGE,
            BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "지엔코스타일",
        shopUrl = "https://gncostyle.com",
        styles = listOf(Style.ROMANTIC, Style.VINTAGE),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_SHORT, BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL,
            BodyType.AVERAGE_SHORT, BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "잇미샤",
        shopUrl = "https://sisun.com/ITMICHAA",
        styles = listOf(Style.MINIMAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL, BodyType.AVERAGE_AVERAGE,
            BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "키르시",
        shopUrl = "https://kirsh.co.kr",
        styles = listOf(Style.CASUAL, Style.ROMANTIC),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_SHORT, BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL,
            BodyType.AVERAGE_SHORT, BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "앤더슨벨",
        shopUrl = "https://anderssonbell.com",
        styles = listOf(Style.MINIMAL, Style.STREET, Style.VINTAGE),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL, BodyType.AVERAGE_AVERAGE,
            BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "마르헨제이",
        shopUrl = "https://www.marhenj.com",
        styles = listOf(Style.MINIMAL, Style.ROMANTIC),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.TEENS),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.FEMALE,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "락피쉬웨더웨어",
        shopUrl = "https://rockfish-weatherwear.co.kr",
        styles = listOf(Style.CASUAL, Style.CLASSIC, Style.MINIMAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_SHORT, BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL,
            BodyType.AVERAGE_SHORT, BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "닥터마틴",
        shopUrl = "https://www.drmartens.co.kr",
        styles = listOf(Style.CLASSIC),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.PREMIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "컨버스",
        shopUrl = "https://www.converse.co.kr",
        styles = listOf(Style.STREET, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "반스",
        shopUrl = "https://www.vans.co.kr",
        styles = listOf(Style.STREET, Style.SPORTS),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "로아드로아",
        shopUrl = "https://roidesrois.co.kr",
        styles = listOf(Style.STREET, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
        priceRange = PriceRange.LOW,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_SHORT, BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL,
            BodyType.AVERAGE_SHORT, BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL,
            BodyType.PLUS_SHORT)
    ),

    RecommendedShop(
        shopName = "이스트로그",
        shopUrl = "https://www.eastlogue.com",
        styles = listOf(Style.CLASSIC, Style.VINTAGE),
        ageGroup = listOf(AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.PREMIUM,
        gender = Gender.MALE,
        body = listOf(BodyType.SLIM_TALL, BodyType.AVERAGE_TALL, BodyType.PLUS_TALL)
    ),

    RecommendedShop(
        shopName = "잭앤질",
        shopUrl = "https://jacknjill.co.kr",
        styles = listOf(Style.CASUAL, Style.MINIMAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "빈폴",
        shopUrl = "https://www.beanpole.com",
        styles = listOf(Style.CLASSIC),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL, BodyType.AVERAGE_AVERAGE,
            BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "엘칸토",
        shopUrl = "https://www.elcanto.co.kr",
        styles = listOf(Style.CLASSIC),
        ageGroup = listOf(AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "탠디",
        shopUrl = "https://www.tandymall.com",
        styles = listOf(Style.CLASSIC),
        ageGroup = listOf(AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "미소페",
        shopUrl = "https://www.misope.co.kr",
        styles = listOf(Style.CLASSIC),
        ageGroup = listOf(AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "쌤소나이트",
        shopUrl = "https://www.samsonite.co.kr",
        styles = listOf(Style.MINIMAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.PREMIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "투미",
        shopUrl = "https://www.tumi.co.kr",
        styles = listOf(Style.MINIMAL, Style.CLASSIC),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.PREMIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "파르티멘토",
        shopUrl = "https://partimento.com",
        styles = listOf(Style.CASUAL, Style.MINIMAL, Style.STREET),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_SHORT, BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL,
            BodyType.AVERAGE_SHORT, BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL,
            BodyType.PLUS_SHORT)
    ),

    RecommendedShop(
        shopName = "알렉산더왕",
        shopUrl = "https://www.alexanderwang.com",
        styles = listOf(Style.MINIMAL, Style.STREET, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.PREMIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_SHORT, BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL)
    ),

    RecommendedShop(
        shopName = "아더에러",
        shopUrl = "https://www.adererror.com",
        styles = listOf(Style.STREET, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.PREMIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL, BodyType.AVERAGE_AVERAGE,
            BodyType.AVERAGE_TALL, BodyType.PLUS_AVERAGE, BodyType.PLUS_TALL)
    ),

    RecommendedShop(
        shopName = "메종키츠네",
        shopUrl = "https://www.maisonkitsune.com",
        styles = listOf(Style.MINIMAL, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.PREMIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_SHORT, BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL,
            BodyType.AVERAGE_SHORT, BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "오프화이트",
        shopUrl = "https://www.off---white.com",
        styles = listOf(Style.STREET),
        ageGroup = listOf(AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.PREMIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_SHORT, BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL,
            BodyType.AVERAGE_SHORT, BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "노스페이스",
        shopUrl = "https://www.thenorthfacekorea.co.kr",
        styles = listOf(Style.SPORTS, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES, AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "파타고니아",
        shopUrl = "https://www.patagonia.com",
        styles = listOf(Style.SPORTS, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "몽클레르",
        shopUrl = "https://www.moncler.com",
        styles = listOf(Style.CLASSIC, Style.MINIMAL),
        ageGroup = listOf(AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.PREMIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL, BodyType.AVERAGE_AVERAGE,
            BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "캐나다구스",
        shopUrl = "https://www.canadagoose.com",
        styles = listOf(Style.CLASSIC, Style.SPORTS),
        ageGroup = listOf(AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.PREMIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL, BodyType.AVERAGE_AVERAGE,
            BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "블랙야크",
        shopUrl = "https://www.blackyak.com",
        styles = listOf(Style.SPORTS, Style.CASUAL),
        ageGroup = listOf(AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "코오롱스포츠",
        shopUrl = "https://www.kolonsport.com",
        styles = listOf(Style.SPORTS, Style.CASUAL),
        ageGroup = listOf(AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "아이더",
        shopUrl = "https://www.eider.co.kr",
        styles = listOf(Style.SPORTS, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES, AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "네파",
        shopUrl = "https://www.nepa.co.kr",
        styles = listOf(Style.SPORTS, Style.CASUAL),
        ageGroup = listOf(AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "데상트",
        shopUrl = "https://dk-on.com/DESCENTE",
        styles = listOf(Style.SPORTS, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_SHORT, BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL,
            BodyType.AVERAGE_SHORT, BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "컬럼비아",
        shopUrl = "https://www.columbiasportswear.co.kr",
        styles = listOf(Style.SPORTS, Style.CASUAL),
        ageGroup = listOf(AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "안다르",
        shopUrl = "https://www.andar.co.kr",
        styles = listOf(Style.SPORTS, Style.MINIMAL),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_SHORT, BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL,
            BodyType.AVERAGE_SHORT, BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "젝시믹스",
        shopUrl = "https://www.xexymix.com",
        styles = listOf(Style.SPORTS, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_SHORT, BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL,
            BodyType.AVERAGE_SHORT, BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "뮬라웨어",
        shopUrl = "https://mulawear.com/",
        styles = listOf(Style.SPORTS, Style.MINIMAL),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_SHORT, BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL,
            BodyType.AVERAGE_SHORT, BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "룰루레몬",
        shopUrl = "https://www.lululemon.co.kr",
        styles = listOf(Style.SPORTS, Style.MINIMAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_SHORT, BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL,
            BodyType.AVERAGE_SHORT, BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "미쏘",
        shopUrl = "https://www.mixxo.com",
        styles = listOf(Style.CASUAL, Style.ROMANTIC, Style.MINIMAL),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.LOW,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_SHORT, BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL,
            BodyType.AVERAGE_SHORT, BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "8seconds",
        shopUrl = "https://www.ssfshop.com/8Seconds",
        styles = listOf(Style.CASUAL, Style.STREET),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_SHORT, BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL,
            BodyType.AVERAGE_SHORT, BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "시티브리즈",
        shopUrl = "https://www.citybreeze.co.kr",
        styles = listOf(Style.MINIMAL, Style.ROMANTIC, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_SHORT, BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL,
            BodyType.AVERAGE_SHORT, BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "체이스컬트",
        shopUrl = "https://www.chasecult.co.kr",
        styles = listOf(Style.CASUAL, Style.SPORTS),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
        priceRange = PriceRange.LOW,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_SHORT, BodyType.SLIM_AVERAGE,
            BodyType.AVERAGE_SHORT, BodyType.AVERAGE_AVERAGE)
    ),

    RecommendedShop(
        shopName = "로렌하이",
        shopUrl = "https://www.laurenhi.com",
        styles = listOf(Style.MINIMAL, Style.ROMANTIC, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_SHORT, BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL,
            BodyType.AVERAGE_SHORT, BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "큐니걸스",
        shopUrl = "https://www.qng.co.kr",
        styles = listOf(Style.ROMANTIC, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TEENS),
        priceRange = PriceRange.LOW,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_SHORT, BodyType.SLIM_AVERAGE,
            BodyType.AVERAGE_SHORT, BodyType.AVERAGE_AVERAGE)
    ),

    RecommendedShop(
        shopName = "고고싱",
        shopUrl = "https://www.ggsing.com",
        styles = listOf(Style.ROMANTIC, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
        priceRange = PriceRange.LOW,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_SHORT, BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL,
            BodyType.AVERAGE_SHORT, BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "위드윤",
        shopUrl = "https://withyoon.com/",
        styles = listOf(Style.MINIMAL, Style.ROMANTIC, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_SHORT, BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL,
            BodyType.AVERAGE_SHORT, BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "디어리스트",
        shopUrl = "https://maybe-baby.co.kr",
        styles = listOf(Style.MINIMAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_SHORT, BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL,
            BodyType.AVERAGE_SHORT, BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "리리앤코",
        shopUrl = "https://ririnco.com",
        styles = listOf(Style.ROMANTIC, Style.MINIMAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_SHORT, BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL,
            BodyType.AVERAGE_SHORT, BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "크럼프",
        shopUrl = "https://crump.co.kr",
        styles = listOf(Style.STREET, Style.SPORTS),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "디어먼트",
        shopUrl = "https://minibbong.co.kr",
        styles = listOf(Style.ROMANTIC, Style.CASUAL, Style.MINIMAL),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_SHORT, BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL,
            BodyType.AVERAGE_SHORT, BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "나이브실루엣",
        shopUrl = "https://naive-silhouette.com",
        styles = listOf(Style.MINIMAL, Style.CLASSIC),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_SHORT, BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL,
            BodyType.AVERAGE_SHORT, BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "무드인슬로우",
        shopUrl = "https://moodinslow.com",
        styles = listOf(Style.ROMANTIC, Style.VINTAGE, Style.MINIMAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_SHORT, BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL,
            BodyType.AVERAGE_SHORT, BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "크렘므",
        shopUrl = "https://cre-me.co.kr",
        styles = listOf(Style.MINIMAL, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_SHORT, BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL,
            BodyType.AVERAGE_SHORT, BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "르헤르",
        shopUrl = "https://lehere.kr",
        styles = listOf(Style.MINIMAL, Style.CLASSIC, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_SHORT, BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL,
            BodyType.AVERAGE_SHORT, BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "루그너",
        shopUrl = "https://lugner.co.kr/",
        styles = listOf(Style.CLASSIC),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_SHORT, BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL,
            BodyType.AVERAGE_SHORT, BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "이바나헬싱키",
        shopUrl = "https://ivanahelsinki.co.kr",
        styles = listOf(Style.CLASSIC, Style.ROMANTIC, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_SHORT, BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL,
            BodyType.AVERAGE_SHORT, BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "오디너리먼트",
        shopUrl = "https://ordinairement.com",
        styles = listOf(Style.CASUAL, Style.ROMANTIC),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_SHORT, BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL,
            BodyType.AVERAGE_SHORT, BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "LOEUVRE",
        shopUrl = "https://maisonloeuvre.com",
        styles = listOf(Style.CLASSIC, Style.MINIMAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_SHORT, BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL,
            BodyType.AVERAGE_SHORT, BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "킨더살몬",
        shopUrl = "https://kindersalmonshop.com",
        styles = listOf(Style.MINIMAL, Style.CLASSIC, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL, BodyType.AVERAGE_AVERAGE,
            BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "리이",
        shopUrl = "https://rerhee.com",
        styles = listOf(Style.MINIMAL, Style.CLASSIC, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL, BodyType.AVERAGE_AVERAGE,
            BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "HOWUS",
        shopUrl = "https://www.howus.co.kr",
        styles = listOf(Style.STREET, Style.MINIMAL, Style.ROMANTIC),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL, BodyType.AVERAGE_AVERAGE,
            BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "아노에틱",
        shopUrl = "https://anoetic.kr",
        styles = listOf(Style.STREET, Style.ROMANTIC),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL, BodyType.AVERAGE_AVERAGE,
            BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "센스",
        shopUrl = "https://www.ssense.com/ko-kr",
        styles = listOf(Style.ALL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.PREMIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "프레이트",
        shopUrl = "https://fr8ight.co.kr",
        styles = listOf(Style.VINTAGE, Style.CASUAL, Style.CLASSIC),
        ageGroup = listOf(AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.PREMIUM,
        gender = Gender.MALE,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL, BodyType.AVERAGE_AVERAGE,
            BodyType.AVERAGE_TALL, BodyType.PLUS_AVERAGE, BodyType.PLUS_TALL)
    ),

    RecommendedShop(
        shopName = "카키스",
        shopUrl = "https://khakis2020.com",
        styles = listOf(Style.CASUAL, Style.CLASSIC, Style.SPORTS),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.MALE,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL, BodyType.AVERAGE_AVERAGE,
            BodyType.AVERAGE_TALL, BodyType.PLUS_AVERAGE, BodyType.PLUS_TALL)
    ),

    RecommendedShop(
        shopName = "젠테스토어",
        shopUrl = "https://www.jentestore.com",
        styles = listOf(Style.ALL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.PREMIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "오케이몰",
        shopUrl = "https://www.okmall.com",
        styles = listOf(Style.ALL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.PREMIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "슬로우스테디클럽",
        shopUrl = "https://slowsteadyclub.com",
        styles = listOf(Style.MINIMAL, Style.CASUAL, Style.VINTAGE, Style.CLASSIC),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.PREMIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "아이엠샵",
        shopUrl = "https://iamshop-online.com",
        styles = listOf(Style.CASUAL, Style.STREET, Style.CLASSIC),
        ageGroup = listOf(AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.PREMIUM,
        gender = Gender.MALE,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL, BodyType.AVERAGE_AVERAGE,
            BodyType.AVERAGE_TALL, BodyType.PLUS_AVERAGE, BodyType.PLUS_TALL)
    ),

    RecommendedShop(
        shopName = "옵스큐라스토어",
        shopUrl = "https://obscura-store.com",
        styles = listOf(Style.CASUAL, Style.MINIMAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.PREMIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL, BodyType.AVERAGE_AVERAGE,
            BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "샵아모멘토",
        shopUrl = "https://shopamomento.com",
        styles = listOf(Style.MINIMAL, Style.ROMANTIC, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.PREMIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL, BodyType.AVERAGE_AVERAGE,
            BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "이티씨서울",
        shopUrl = "https://etcseoul.com",
        styles = listOf(Style.CASUAL, Style.STREET),
        ageGroup = listOf(AgeGroup.THIRTIES, AgeGroup.TWENTIES),
        priceRange = PriceRange.PREMIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL, BodyType.AVERAGE_AVERAGE,
            BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "에잇디비전",
        shopUrl = "https://8division.com",
        styles = listOf(Style.STREET, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.PREMIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL, BodyType.AVERAGE_AVERAGE,
            BodyType.AVERAGE_TALL, BodyType.PLUS_AVERAGE, BodyType.PLUS_TALL)
    ),

    RecommendedShop(
        shopName = "SSF SHOP",
        shopUrl = "https://www.ssfshop.com",
        styles = listOf(Style.ALL),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES, AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.PREMIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "애딕티드서울",
        shopUrl = "https://www.addictedseoul.com",
        styles = listOf(Style.STREET, Style.CASUAL),
        ageGroup = listOf(AgeGroup.THIRTIES, AgeGroup.TWENTIES),
        priceRange = PriceRange.PREMIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL, BodyType.AVERAGE_AVERAGE,
            BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "더엑스샵",
        shopUrl = "https://thexshop.co.kr",
        styles = listOf(Style.STREET, Style.SPORTS),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES, AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL, BodyType.AVERAGE_AVERAGE,
            BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "하바티",
        shopUrl = "https://havatishop.com",
        styles = listOf(Style.CLASSIC, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.PREMIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL, BodyType.AVERAGE_AVERAGE,
            BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "나이트웍스",
        shopUrl = "https://nightwaks.com",
        styles = listOf(Style.CASUAL, Style.STREET),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.PREMIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL, BodyType.AVERAGE_AVERAGE,
            BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "노클레임",
        shopUrl = "https://noclaim.co.kr",
        styles = listOf(Style.CASUAL, Style.STREET),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.PREMIUM,
        gender = Gender.MALE,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL, BodyType.AVERAGE_AVERAGE,
            BodyType.AVERAGE_TALL, BodyType.PLUS_AVERAGE, BodyType.PLUS_TALL)
    ),

    RecommendedShop(
        shopName = "apart from that",
        shopUrl = "https://apartfromthat-store.com",
        styles = listOf(Style.MINIMAL, Style.CLASSIC, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.PREMIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL, BodyType.AVERAGE_AVERAGE,
            BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "러드",
        shopUrl = "https://llud.co.kr",
        styles = listOf(Style.STREET),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.TEENS),
        priceRange = PriceRange.HIGH,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL, BodyType.AVERAGE_AVERAGE,
            BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "포터리",
        shopUrl = "https://ptry.co.kr",
        styles = listOf(Style.CLASSIC, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.SLIM_TALL, BodyType.AVERAGE_AVERAGE,
            BodyType.AVERAGE_TALL)
    )
)