package com.example.test908.data.repository.books.limitedSeries

import com.example.test908.data.repository.books.limitedSeries.model.BookOfferDto
import javax.inject.Inject

class LimitedSeriesApiImpl
    @Inject
    constructor() : LimitedSeriesApi {
        override suspend fun status(): LimitedSeriesRegistrationStatus {
            return LimitedSeriesRegistrationStatus.NOT_STARTED
        }

        override suspend fun getBookOffers(): List<BookOfferDto> {
            val list = mutableListOf<BookOfferDto>()
            list.add(
                0,
                BookOfferDto(
                    "0",
                    "2023-01-11",
                    "1984: 75th Anniversary",
                    "Written 75 years ago, 1984 was George Orwell’s chilling prophecy about the future. And while 1984 has come and gone, his dystopian vision of a government that will do anything to control the narrative is timelier than ever...",
                    "$7.84",
                ),
            )
            list.add(
                1,
                BookOfferDto(
                    "1",
                    "2023-01-12",
                    "We: A Novel",
                    "The chilling dystopian novel that influenced George Orwell while he was writing 1984, with a new introduction by Margaret Atwood and an essay by Ursula Le Guin",
                    "$8.99",
                ),
            )
            list.add(
                2,
                BookOfferDto(
                    "2",
                    "2023-01-09",
                    "Alone: A Paranormal Mystery Thriller (The Girl in the Box Book 1)",
                    "Sienna Nealon was a 17 year-old girl who had been held prisoner in her own house by her mother for twelve years. Then one day her mother vanished, and Sienna woke up to find two strange men in her home. On the run, unsure of who to turn to and discovering she possesses mysterious powers, Sienna finds herself pursued by a shadowy agency known as the Directorate and hunted by a vicious psychopath named Wolfe, each of which is determined to capture her for their own purposes...",
                    "$4.99",
                ),
            )
            list.add(
                3,
                BookOfferDto(
                    "3",
                    "2023-05-09",
                    "Omega (The Girl in the Box Book 5) ",
                    "Omega - a shadowy organization that is synonymous with power in the metahuman world. They have hunted Sienna Nealon since the day she first left her house, have killed countless Directorate agents and operatives, and now they unveil their greatest plot - Operation Stanchion, a mysterious phrase let slip by an Omega operative in the midst of a battle. Now Sienna must track the pieces Omega has in motion to confront her enemy before they can land their final stroke - and bring an end to the Directorate forever.",
                    "$4.99",
                ),
            )
            list.add(
                4,
                BookOfferDto(
                    "4",
                    "2023-03-03",
                    "The Rebellious Sister (Unstoppable Liv Beaufont Book 1)",
                    "Liv is a rebel with royal blood who abdicated her birthright. A string of murders changes everything and the House of Seven asks her to take on a role as a Warrior, one of seven positions revered for protecting magic.",
                    "$13",
                ),
            )
            list.add(
                5,
                BookOfferDto(
                    "5",
                    "2002-02-02",
                    "Great Expectations (Dover Thrift Editions: Classic Novels)",
                    "In this unflaggingly suspenseful story of aspirations and moral redemption, humble, orphaned Pip, a ward of his short-tempered older sister and her husband, Joe, is apprenticed to the dirty work of the forge but dares to dream of becoming a gentleman. And, indeed, it seems as though that dream is destined to come to pass — because one day, under sudden and enigmatic circumstances, he finds himself in possession of \"great expectations.\"",
                    "$2.34",
                ),
            )
            list.add(
                6,
                BookOfferDto(
                    "6",
                    "2023-01-02",
                    "SPIRITUAL TALES OF YESTERYEAR: Tales with a spiritual twist received from Charles Dickens through Automatic Writing",
                    "This book of tales of yesteryear is product of a spiritual contact received through automatic writing, by the British born medium Ian Purdy with the novelist Charles Dickens who died in 1870.",
                    "$9.99",
                ),
            )
            list.add(
                7,
                BookOfferDto(
                    "7",
                    "2015-08-03",
                    "Get The Yak Out",
                    "Meet Ben, whose philosophy in life is to choose the path of least resistance. But alas, even the most indifferent hipster can't escape the curse of a Friday that unfolds like a tragicomedy. Maybe he should have declared \"CBB\" before emerging from the sanctuary of his bed.",
                    "$2.29",
                ),
            )
            list.add(
                8,
                BookOfferDto(
                    "8",
                    "2012-12-12",
                    "1984: 75th Anniversary",
                    "Written 75 years ago, 1984 was George Orwell’s chilling prophecy about the future. And while 1984 has come and gone, his dystopian vision of a government that will do anything to control the narrative is timelier than ever...",
                    "$7.84",
                ),
            )
            list.add(
                9,
                BookOfferDto(
                    "9",
                    "2022-12-11",
                    "We: A Novel",
                    "The chilling dystopian novel that influenced George Orwell while he was writing 1984, with a new introduction by Margaret Atwood and an essay by Ursula Le Guin",
                    "$8.99",
                ),
            )
            list.add(
                10,
                BookOfferDto(
                    "10",
                    "2023-01-01",
                    "Alone: A Paranormal Mystery Thriller (The Girl in the Box Book 1)",
                    "Sienna Nealon was a 17 year-old girl who had been held prisoner in her own house by her mother for twelve years. Then one day her mother vanished, and Sienna woke up to find two strange men in her home. On the run, unsure of who to turn to and discovering she possesses mysterious powers, Sienna finds herself pursued by a shadowy agency known as the Directorate and hunted by a vicious psychopath named Wolfe, each of which is determined to capture her for their own purposes...",
                    "$4.99",
                ),
            )
            list.add(
                11,
                BookOfferDto(
                    "11",
                    "2023-01-23",
                    "Omega (The Girl in the Box Book 5) ",
                    "Omega - a shadowy organization that is synonymous with power in the metahuman world. They have hunted Sienna Nealon since the day she first left her house, have killed countless Directorate agents and operatives, and now they unveil their greatest plot - Operation Stanchion, a mysterious phrase let slip by an Omega operative in the midst of a battle. Now Sienna must track the pieces Omega has in motion to confront her enemy before they can land their final stroke - and bring an end to the Directorate forever.",
                    "$4.99",
                ),
            )
            list.add(
                12,
                BookOfferDto(
                    "12",
                    "2020-02-02",
                    "The Rebellious Sister (Unstoppable Liv Beaufont Book 1)",
                    "Liv is a rebel with royal blood who abdicated her birthright. A string of murders changes everything and the House of Seven asks her to take on a role as a Warrior, one of seven positions revered for protecting magic.",
                    "$13",
                ),
            )
            list.add(
                13,
                BookOfferDto(
                    "13",
                    "2019-01-12",
                    "Great Expectations (Dover Thrift Editions: Classic Novels)",
                    "In this unflaggingly suspenseful story of aspirations and moral redemption, humble, orphaned Pip, a ward of his short-tempered older sister and her husband, Joe, is apprenticed to the dirty work of the forge but dares to dream of becoming a gentleman. And, indeed, it seems as though that dream is destined to come to pass — because one day, under sudden and enigmatic circumstances, he finds himself in possession of \"great expectations.\"",
                    "$2.34",
                ),
            )
            list.add(
                14,
                BookOfferDto(
                    "14",
                    "2000-01-12",
                    "SPIRITUAL TALES OF YESTERYEAR: Tales with a spiritual twist received from Charles Dickens through Automatic Writing",
                    "This book of tales of yesteryear is product of a spiritual contact received through automatic writing, by the British born medium Ian Purdy with the novelist Charles Dickens who died in 1870.",
                    "$9.99",
                ),
            )
            list.add(
                15,
                BookOfferDto(
                    "15",
                    "2024-01-29",
                    "Get The Yak Out",
                    "Meet Ben, whose philosophy in life is to choose the path of least resistance. But alas, even the most indifferent hipster can't escape the curse of a Friday that unfolds like a tragicomedy. Maybe he should have declared \"CBB\" before emerging from the sanctuary of his bed.",
                    "$2.29",
                ),
            )
            list.add(
                16,
                BookOfferDto(
                    "16",
                    "2002-01-20",
                    "1984: 75th Anniversary",
                    "Written 75 years ago, 1984 was George Orwell’s chilling prophecy about the future. And while 1984 has come and gone, his dystopian vision of a government that will do anything to control the narrative is timelier than ever...",
                    "$00000000",
                ),
            )
            list.add(
                17,
                BookOfferDto(
                    "17",
                    "2023-01-11",
                    "1984: 75th Anniversary",
                    "Written 75 years ago, 1984 was George Orwell’s chilling prophecy about the future. And while 1984 has come and gone, his dystopian vision of a government that will do anything to control the narrative is timelier than ever...",
                    "$7.84",
                ),
            )
            list.add(
                18,
                BookOfferDto(
                    "18",
                    "2002-01-20",
                    "1984: 75th Anniversary",
                    "Written 75 years ago, 1984 was George Orwell’s chilling prophecy about the future. And while 1984 has come and gone, his dystopian vision of a government that will do anything to control the narrative is timelier than ever...",
                    "$00000000",
                ),
            )
            return list
        }

        override suspend fun startRegistration() {
            //  delay(2000L)
        }

        override suspend fun postCompleteRegistrationParams(params: PostCompleteRegistrationsParams) {
            //   delay(2000L)
        }
    }
