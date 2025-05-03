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
        priceRange = PriceRange.MEDIUM,
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
        priceRange = PriceRange.MEDIUM,
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
        shopName = "GU",
        shopUrl = "https://www.gu-global.com/kr",
        styles = listOf(Style.ALL),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES, AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "나이키",
        shopUrl = "https://www.nike.com/kr",
        styles = listOf(Style.ALL),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES, AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "아디다스",
        shopUrl = "https://www.adidas.co.kr",
        styles = listOf(Style.SPORTS, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "뉴발란스",
        shopUrl = "https://www.nbkorea.com",
        styles = listOf(Style.SPORTS, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "퓨마",
        shopUrl = "https://kr.puma.com",
        styles = listOf(Style.SPORTS),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "MLB",
        shopUrl = "https://www.mlb-korea.com",
        styles = listOf(Style.STREET, Style.SPORTS),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
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
        shopUrl = "https://kr.tommy.com",
        styles = listOf(Style.CLASSIC, Style.FORMAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.PREMIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "자라",
        shopUrl = "https://www.zara.com/kr",
        styles = listOf(Style.MODERN, Style.CLASSIC),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "H&M",
        shopUrl = "https://www2.hm.com/ko_kr/index.html",
        styles = listOf(Style.CASUAL, Style.MINIMAL),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
        priceRange = PriceRange.LOW,
        gender = Gender.UNISEX,
        body = listOf(BodyType.ALL)
    ),

    RecommendedShop(
        shopName = "탑텐",
        shopUrl = "https://www.topten10mall.com",
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
        shopUrl = "https://nerdy.co.kr",
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
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.AVERAGE_AVERAGE)
    ),

    RecommendedShop(
        shopName = "오아이오아이",
        shopUrl = "https://5252byoioi.com",
        styles = listOf(Style.ROMANTIC, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.AVERAGE_SHORT)
    ),

    RecommendedShop(
        shopName = "오아이오아이컬렉션",
        shopUrl = "https://oioicollection.com",
        styles = listOf(Style.FORMAL, Style.CLASSIC),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_TALL, BodyType.AVERAGE_AVERAGE)
    ),

    RecommendedShop(
        shopName = "비바스튜디오",
        shopUrl = "https://vivastudio.co.kr",
        styles = listOf(Style.MINIMAL, Style.CLASSIC),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.MALE,
        body = listOf(BodyType.AVERAGE_TALL, BodyType.SLIM_TALL)
    ),

    RecommendedShop(
        shopName = "마르디메크르디",
        shopUrl = "https://mardimercredi.com",
        styles = listOf(Style.ROMANTIC, Style.CLASSIC),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.AVERAGE_AVERAGE)
    ),

    RecommendedShop(
        shopName = "더오픈프로덕트",
        shopUrl = "https://theopenproduct.com",
        styles = listOf(Style.MINIMAL, Style.MODERN),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.PREMIUM,
        gender = Gender.FEMALE,
        body = listOf(BodyType.AVERAGE_TALL, BodyType.SLIM_TALL)
    ),

    RecommendedShop(
        shopName = "르917",
        shopUrl = "https://le917.com",
        styles = listOf(Style.MINIMAL, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.FEMALE,
        body = listOf(BodyType.AVERAGE_AVERAGE)
    ),

    RecommendedShop(
        shopName = "로씨로씨",
        shopUrl = "https://rococoroco.com",
        styles = listOf(Style.ROMANTIC, Style.VINTAGE),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_SHORT, BodyType.AVERAGE_SHORT)
    ),

    RecommendedShop(
        shopName = "아워코모스",
        shopUrl = "https://ourcomos.com",
        styles = listOf(Style.MODERN, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.FEMALE,
        body = listOf(BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "로맨틱크라운",
        shopUrl = "https://www.romanticcrown.com",
        styles = listOf(Style.CASUAL, Style.STREET),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "비슬로우",
        shopUrl = "https://beslow.co.kr",
        styles = listOf(Style.MINIMAL, Style.CLASSIC),
        ageGroup = listOf(AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.MALE,
        body = listOf(BodyType.AVERAGE_TALL, BodyType.PLUS_AVERAGE)
    ),

    RecommendedShop(
        shopName = "코스",
        shopUrl = "https://www.cosstores.com",
        styles = listOf(Style.MODERN, Style.MINIMAL),
        ageGroup = listOf(AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.UNISEX,
        body = listOf(BodyType.AVERAGE_AVERAGE, BodyType.SLIM_TALL)
    ),

    RecommendedShop(
        shopName = "시스템",
        shopUrl = "https://www.system.co.kr",
        styles = listOf(Style.FORMAL, Style.MODERN),
        ageGroup = listOf(AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.FEMALE,
        body = listOf(BodyType.AVERAGE_AVERAGE, BodyType.SLIM_AVERAGE)
    ),

    RecommendedShop(
        shopName = "써스데이아일랜드",
        shopUrl = "https://thursdayisland.co.kr",
        styles = listOf(Style.ROMANTIC, Style.VINTAGE),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.AVERAGE_SHORT)
    ),

    RecommendedShop(
        shopName = "잇미샤",
        shopUrl = "https://www.itmichaa.com",
        styles = listOf(Style.FORMAL, Style.CLASSIC),
        ageGroup = listOf(AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.FEMALE,
        body = listOf(BodyType.AVERAGE_AVERAGE, BodyType.SLIM_AVERAGE)
    ),

    RecommendedShop(
        shopName = "키르시",
        shopUrl = "https://kirsh.co.kr",
        styles = listOf(Style.CASUAL, Style.ROMANTIC),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
        priceRange = PriceRange.LOW,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_SHORT, BodyType.AVERAGE_SHORT)
    ),

    RecommendedShop(
        shopName = "앤더슨벨",
        shopUrl = "https://anderssonbell.com",
        styles = listOf(Style.MODERN, Style.STREET),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.UNISEX,
        body = listOf(BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "뎁",
        shopUrl = "https://www.debb.co.kr",
        styles = listOf(Style.ROMANTIC, Style.CLASSIC),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_SHORT, BodyType.SLIM_AVERAGE)
    ),

    RecommendedShop(
        shopName = "뽐므옴므",
        shopUrl = "https://pommehomme.com",
        styles = listOf(Style.MINIMAL, Style.FORMAL),
        ageGroup = listOf(AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.MALE,
        body = listOf(BodyType.SLIM_TALL, BodyType.AVERAGE_TALL)
    ),


    RecommendedShop(
        shopName = "마르헨제이",
        shopUrl = "https://www.marhenj.com",
        styles = listOf(Style.MINIMAL, Style.ROMANTIC),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.TEENS),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.AVERAGE_SHORT)
    ),

    RecommendedShop(
        shopName = "락피쉬",
        shopUrl = "https://www.rockfishweatherwear.com",
        styles = listOf(Style.CASUAL, Style.CLASSIC),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "닥터마틴",
        shopUrl = "https://www.drmartens.co.kr",
        styles = listOf(Style.PUNK, Style.CLASSIC),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
        priceRange = PriceRange.PREMIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_TALL, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "컨버스",
        shopUrl = "https://www.converse.co.kr",
        styles = listOf(Style.STREET, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.AVERAGE_AVERAGE)
    ),

    RecommendedShop(
        shopName = "반스",
        shopUrl = "https://www.vans.co.kr",
        styles = listOf(Style.STREET, Style.SPORTS),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_SHORT, BodyType.SLIM_AVERAGE)
    ),

    RecommendedShop(
        shopName = "플랫에이트",
        shopUrl = "https://www.flat-eight.com",
        styles = listOf(Style.MINIMAL, Style.CLASSIC),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.FEMALE,
        body = listOf(BodyType.AVERAGE_AVERAGE, BodyType.SLIM_AVERAGE)
    ),

    RecommendedShop(
        shopName = "르헤브",
        shopUrl = "https://www.lehue.co.kr",
        styles = listOf(Style.ROMANTIC, Style.CLASSIC),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_SHORT, BodyType.SLIM_AVERAGE)
    ),

    RecommendedShop(
        shopName = "로아드로아",
        shopUrl = "https://www.roaroa.com",
        styles = listOf(Style.STREET, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_SHORT, BodyType.AVERAGE_SHORT)
    ),

    RecommendedShop(
        shopName = "이스트로그",
        shopUrl = "https://www.eastlogue.com",
        styles = listOf(Style.CLASSIC, Style.VINTAGE),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.PREMIUM,
        gender = Gender.MALE,
        body = listOf(BodyType.AVERAGE_TALL, BodyType.PLUS_TALL)
    ),

    RecommendedShop(
        shopName = "잭앤질",
        shopUrl = "https://www.jackjillmall.com",
        styles = listOf(Style.CASUAL, Style.MODERN),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_SHORT)
    ),

    RecommendedShop(
        shopName = "클루",
        shopUrl = "https://www.clue.co.kr",
        styles = listOf(Style.ROMANTIC, Style.CLASSIC),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_SHORT, BodyType.AVERAGE_SHORT)
    ),

    RecommendedShop(
        shopName = "빈폴",
        shopUrl = "https://www.beanpole.com",
        styles = listOf(Style.CLASSIC, Style.FORMAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.UNISEX,
        body = listOf(BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "올젠",
        shopUrl = "https://www.olzen.co.kr",
        styles = listOf(Style.CLASSIC, Style.MODERN),
        ageGroup = listOf(AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.MALE,
        body = listOf(BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "지오지아",
        shopUrl = "https://www.ziozia.co.kr",
        styles = listOf(Style.FORMAL, Style.MODERN),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.MALE,
        body = listOf(BodyType.SLIM_TALL, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "엘칸토",
        shopUrl = "https://www.elcanto.co.kr",
        styles = listOf(Style.FORMAL, Style.CLASSIC),
        ageGroup = listOf(AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.AVERAGE_AVERAGE, BodyType.PLUS_AVERAGE)
    ),

    RecommendedShop(
        shopName = "탠디",
        shopUrl = "https://www.tandy.co.kr",
        styles = listOf(Style.FORMAL, Style.CLASSIC),
        ageGroup = listOf(AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.PREMIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "미소페",
        shopUrl = "https://www.missope.co.kr",
        styles = listOf(Style.FORMAL, Style.CLASSIC),
        ageGroup = listOf(AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.UNISEX,
        body = listOf(BodyType.AVERAGE_AVERAGE, BodyType.PLUS_AVERAGE)
    ),

    RecommendedShop(
        shopName = "샘소나이트",
        shopUrl = "https://www.samsonite.co.kr",
        styles = listOf(Style.MODERN, Style.MINIMAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.PREMIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL, BodyType.PLUS_TALL)
    ),

    RecommendedShop(
        shopName = "투미",
        shopUrl = "https://www.tumi.co.kr",
        styles = listOf(Style.MINIMAL, Style.CLASSIC),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.PREMIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "파르티멘토",
        shopUrl = "https://www.partimento.kr",
        styles = listOf(Style.CASUAL, Style.MINIMAL, Style.STREET),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.AVERAGE_AVERAGE)
    ),

    RecommendedShop(
        shopName = "알렉산더왕",
        shopUrl = "https://www.alexanderwang.com",
        styles = listOf(Style.MINIMAL, Style.MODERN, Style.FORMAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.PREMIUM,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.AVERAGE_AVERAGE)
    ),

    RecommendedShop(
        shopName = "아더에러",
        shopUrl = "https://www.adererror.com",
        styles = listOf(Style.STREET, Style.MODERN, Style.PUNK),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
        priceRange = PriceRange.PREMIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.AVERAGE_AVERAGE, BodyType.PLUS_AVERAGE)
    ),

    RecommendedShop(
        shopName = "메종키츠네",
        shopUrl = "https://www.maisonkitsune.com",
        styles = listOf(Style.MINIMAL, Style.CASUAL, Style.MODERN),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.PREMIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.AVERAGE_AVERAGE)
    ),

    RecommendedShop(
        shopName = "오프화이트",
        shopUrl = "https://www.off---white.com",
        styles = listOf(Style.PUNK, Style.STREET, Style.FORMAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.PREMIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_TALL, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "노스페이스",
        shopUrl = "https://www.thenorthfacekorea.co.kr",
        styles = listOf(Style.SPORTS, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.UNISEX,
        body = listOf(BodyType.AVERAGE_AVERAGE, BodyType.PLUS_AVERAGE, BodyType.PLUS_TALL)
    ),

    RecommendedShop(
        shopName = "파타고니아",
        shopUrl = "https://www.patagonia.com",
        styles = listOf(Style.SPORTS, Style.MINIMAL, Style.BOHEMIAN),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.UNISEX,
        body = listOf(BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "몽클레르",
        shopUrl = "https://www.moncler.com",
        styles = listOf(Style.FORMAL, Style.CLASSIC),
        ageGroup = listOf(AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.PREMIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.AVERAGE_TALL, BodyType.PLUS_TALL)
    ),

    RecommendedShop(
        shopName = "캐나다구스",
        shopUrl = "https://www.canadagoose.com",
        styles = listOf(Style.FORMAL, Style.SPORTS),
        ageGroup = listOf(AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.PREMIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.AVERAGE_TALL, BodyType.PLUS_TALL)
    ),

    RecommendedShop(
        shopName = "블랙야크",
        shopUrl = "https://www.blackyak.com",
        styles = listOf(Style.SPORTS, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "코오롱스포츠",
        shopUrl = "https://www.kolonsport.com",
        styles = listOf(Style.SPORTS, Style.MINIMAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.UNISEX,
        body = listOf(BodyType.AVERAGE_AVERAGE, BodyType.PLUS_AVERAGE)
    ),

    RecommendedShop(
        shopName = "아이더",
        shopUrl = "https://www.eider.co.kr",
        styles = listOf(Style.SPORTS, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.AVERAGE_AVERAGE, BodyType.PLUS_AVERAGE)
    ),

    RecommendedShop(
        shopName = "네파",
        shopUrl = "https://www.nepa.co.kr",
        styles = listOf(Style.SPORTS, Style.FORMAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.UNISEX,
        body = listOf(BodyType.AVERAGE_TALL, BodyType.PLUS_TALL)
    ),

    RecommendedShop(
        shopName = "데상트",
        shopUrl = "https://www.descente.co.kr",
        styles = listOf(Style.SPORTS, Style.MINIMAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_TALL, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "콜롬비아",
        shopUrl = "https://www.columbiasportswear.co.kr",
        styles = listOf(Style.SPORTS, Style.CLASSIC),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.AVERAGE_AVERAGE, BodyType.PLUS_AVERAGE)
    ),

    RecommendedShop(
        shopName = "안다르",
        shopUrl = "https://www.andar.co.kr",
        styles = listOf(Style.SPORTS, Style.MINIMAL),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.AVERAGE_AVERAGE)
    ),

    RecommendedShop(
        shopName = "젝시믹스",
        shopUrl = "https://www.xexymix.com",
        styles = listOf(Style.SPORTS, Style.MODERN),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.AVERAGE_AVERAGE, BodyType.PLUS_AVERAGE)
    ),

    RecommendedShop(
        shopName = "뮬라웨어",
        shopUrl = "https://www.mullawear.co.kr",
        styles = listOf(Style.SPORTS, Style.MODERN),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.AVERAGE_AVERAGE)
    ),

    RecommendedShop(
        shopName = "룰루레몬",
        shopUrl = "https://www.lululemon.co.kr",
        styles = listOf(Style.SPORTS, Style.MINIMAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.PREMIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "트렌디올",
        shopUrl = "https://www.trendyol.com",
        styles = listOf(Style.CASUAL, Style.FORMAL, Style.ROMANTIC),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
        priceRange = PriceRange.LOW,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_SHORT, BodyType.SLIM_AVERAGE, BodyType.AVERAGE_SHORT)
    ),

    RecommendedShop(
        shopName = "미쏘",
        shopUrl = "https://www.mixxo.com",
        styles = listOf(Style.CASUAL, Style.FORMAL, Style.ROMANTIC),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.LOW,
        gender = Gender.FEMALE,
        body = listOf(BodyType.AVERAGE_SHORT, BodyType.AVERAGE_AVERAGE)
    ),

    RecommendedShop(
        shopName = "8seconds",
        shopUrl = "https://www.ssfshop.com/8Seconds",
        styles = listOf(Style.CASUAL, Style.STREET),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.AVERAGE_AVERAGE)
    ),

    RecommendedShop(
        shopName = "에잇세컨즈",
        shopUrl = "https://www.ssfshop.com/8Seconds",
        styles = listOf(Style.CASUAL, Style.MINIMAL),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.AVERAGE_SHORT, BodyType.AVERAGE_AVERAGE)
    ),

    RecommendedShop(
        shopName = "시티브리즈",
        shopUrl = "https://www.citybreeze.co.kr",
        styles = listOf(Style.MODERN, Style.ROMANTIC),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.AVERAGE_AVERAGE)
    ),

    RecommendedShop(
        shopName = "체이스컬트",
        shopUrl = "https://www.chasecult.co.kr",
        styles = listOf(Style.CASUAL, Style.SPORTS),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
        priceRange = PriceRange.LOW,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.AVERAGE_AVERAGE)
    ),

    RecommendedShop(
        shopName = "스타일난다",
        shopUrl = "https://www.stylenanda.com",
        styles = listOf(Style.STREET, Style.ROMANTIC),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.AVERAGE_SHORT)
    ),

    RecommendedShop(
        shopName = "츄",
        shopUrl = "https://www.chuu.co.kr",
        styles = listOf(Style.ROMANTIC, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_SHORT, BodyType.SLIM_AVERAGE)
    ),

    RecommendedShop(
        shopName = "믹스엑스믹스",
        shopUrl = "https://www.mixxmix.com",
        styles = listOf(Style.STREET, Style.PUNK),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
        priceRange = PriceRange.LOW,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.AVERAGE_SHORT)
    ),

    RecommendedShop(
        shopName = "로렌하이",
        shopUrl = "https://www.laurenhi.com",
        styles = listOf(Style.MINIMAL, Style.ROMANTIC),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.AVERAGE_AVERAGE)
    ),

    RecommendedShop(
        shopName = "소녀나라",
        shopUrl = "https://www.qng.co.kr",
        styles = listOf(Style.ROMANTIC, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TEENS),
        priceRange = PriceRange.LOW,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_SHORT, BodyType.AVERAGE_SHORT)
    ),

    RecommendedShop(
        shopName = "고고싱",
        shopUrl = "https://www.ggsing.com",
        styles = listOf(Style.ROMANTIC, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
        priceRange = PriceRange.LOW,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_SHORT, BodyType.AVERAGE_SHORT)
    ),

    RecommendedShop(
        shopName = "위드윤",
        shopUrl = "https://withyoon.co.kr",
        styles = listOf(Style.MINIMAL, Style.ROMANTIC),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.AVERAGE_AVERAGE)
    ),

    RecommendedShop(
        shopName = "메이비베이비",
        shopUrl = "https://maybe-baby.co.kr",
        styles = listOf(Style.MINIMAL, Style.MODERN),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.HIGH,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.AVERAGE_AVERAGE)
    ),

    RecommendedShop(
        shopName = "리리앤코",
        shopUrl = "https://ririnco.com",
        styles = listOf(Style.ROMANTIC, Style.CLASSIC),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.FEMALE,
        body = listOf(BodyType.AVERAGE_SHORT, BodyType.AVERAGE_AVERAGE)
    ),

    RecommendedShop(
        shopName = "핑크시슬리",
        shopUrl = "https://pinksisly.com",
        styles = listOf(Style.CASUAL, Style.ROMANTIC),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
        priceRange = PriceRange.LOW,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_SHORT, BodyType.AVERAGE_SHORT)
    ),

    RecommendedShop(
        shopName = "베이지크",
        shopUrl = "https://beigic.com",
        styles = listOf(Style.MINIMAL, Style.MODERN),
        ageGroup = listOf(AgeGroup.THIRTIES, AgeGroup.FORTIES),
        priceRange = PriceRange.PREMIUM,
        gender = Gender.FEMALE,
        body = listOf(BodyType.AVERAGE_AVERAGE)
    ),

    RecommendedShop(
        shopName = "리에뜨",
        shopUrl = "https://rietoffical.com",
        styles = listOf(Style.ROMANTIC, Style.VINTAGE),
        ageGroup = listOf(AgeGroup.TWENTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_SHORT, BodyType.AVERAGE_SHORT)
    ),

    RecommendedShop(
        shopName = "안녕윤수야",
        shopUrl = "https://yoonsooya.com",
        styles = listOf(Style.ROMANTIC, Style.CLASSIC),
        ageGroup = listOf(AgeGroup.TWENTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.FEMALE,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.AVERAGE_AVERAGE)
    ),

    RecommendedShop(
        shopName = "고워크",
        shopUrl = "https://gowalk.co.kr",
        styles = listOf(Style.SPORTS, Style.CASUAL),
        ageGroup = listOf(AgeGroup.TWENTIES, AgeGroup.THIRTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.AVERAGE_AVERAGE, BodyType.AVERAGE_TALL)
    ),

    RecommendedShop(
        shopName = "크럼프",
        shopUrl = "https://crump.co.kr",
        styles = listOf(Style.STREET, Style.SPORTS, Style.PUNK),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.AVERAGE_AVERAGE)
    ),

    RecommendedShop(
        shopName = "모어댄도프",
        shopUrl = "https://morethandope.com",
        styles = listOf(Style.STREET, Style.PUNK),
        ageGroup = listOf(AgeGroup.TEENS, AgeGroup.TWENTIES),
        priceRange = PriceRange.MEDIUM,
        gender = Gender.UNISEX,
        body = listOf(BodyType.SLIM_AVERAGE, BodyType.AVERAGE_AVERAGE)
    )
)