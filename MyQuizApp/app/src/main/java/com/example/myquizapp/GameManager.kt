package com.example.myquizapp

import com.example.myquizapp.ui.theme.ui.PlayerScore
import com.example.myquizapp.ui.theme.ui.Question

object GameManager {
    val leaderboard = mutableListOf<PlayerScore>()

    val questionList = listOf(
        Question(
            "Qual é o brigadeiro?",
            listOf("Bolinho de chocolate", "Bala de coco", "Doce de leite", "Bombom de morango"),
            "Bolinho de chocolate",
            "https://diaadianoticia.com.br/wp-content/uploads/2021/12/chocolate.jpg"
        ),
        Question(
            "Qual doce é feito com leite condensado, coco e manteiga?",
            listOf("Brigadeiro", "Beijinho", "Cajuzinho", "Olho de sogra"),
            "Beijinho",
            "https://conteudo.imguol.com.br/c/entretenimento/f3/2021/07/30/coco-1627655478157_v2_450x337.jpg"
        ),
        Question(
            "Qual doce é conhecido por sua cor rosa e sabor de morango?",
            listOf("Brigadeiro", "Beijinho", "Bicho de pé", "Olho de sogra"),
            "Bicho de pé",
            "https://conteudo.imguol.com.br/c/entretenimento/78/2018/02/28/morango-1519823853148_v2_4x3.jpg"
        ),
        Question(
            "Qual doce é feito com leite condensado, coco e ameixas?",
            listOf("Brigadeiro", "Beijinho", "Bicho de pé", "Olho de sogra"),
            "Olho de sogra",
            "https://static.mundoeducacao.uol.com.br/mundoeducacao/conteudo_legenda/5620a6ed95faaf2d79d6ff1c2f2d4f91.jpg"
        ),
        Question(
            "Qual é o doce tradicional feito com amendoim torrado e açúcar caramelizado no Brasil?",
            listOf("Paçoca", "Pé de moleque", "Cajuzinho", "Rapadura"),
            "Pé de moleque",
            "https://images.tcdn.com.br/img/img_prod/1002447/amendoim_torrado_sem_sal_291_1_6e7e79410c8a846c0ea2b804b3567778.jpg"
        ),
        Question(
            "Qual é o doce de origem nordestina feito com massa de mandioca e coco ralado?",
            listOf("Bolo de rolo", "Cuscuz", "Canjica", "Pamonha"),
            "Canjica",
            "https://www.easyanddelish.com/wp-content/uploads/2022/07/Mandioca-aipim-ou-macaxeira-cassava-root-image-by-Aamulya.jpg"
        ),
        Question(
            "Qual é o doce típico do estado do Rio de Janeiro feito com banana, açúcar e canela?",
            listOf("Romeu e Julieta", "Quindim", "Pudim", "Banana caramelizada"),
            "Banana caramelizada",
            "https://www.civitatis.com/blog/wp-content/uploads/2022/10/panoramica-rio-janeiro-brasil.jpg7"
        ),
        Question(
            "Qual é o doce brasileiro feito com tapioca, leite de coco e coco ralado?",
            listOf("Pé de moleque", "Quindim", "Bolo de rolo", "Tapioca"),
            "Tapioca",
            "https://img.cybercook.com.br/receitas/788/tapioca-3.jpeg"
        ),
        Question(
            "Qual é o doce típico de festas juninas feito com milho verde, leite condensado e coco?",
            listOf("Paçoca", "Canjica", "Pé de moleque", "Pamonha"),
            "Pamonha",
            "https://www.oxentemenina.com/wp-content/uploads/2022/06/festa-junina-nordeste-gws-2022-01.jpg"
        ),
        Question(
            "Qual é o doce brasileiro feito com leite, açúcar, gemas de ovo e coco ralado?",
            listOf("Romeu e Julieta", "Quindim", "Pudim", "Cocada"),
            "Quindim",
            "https://emporioxingu.com/wp-content/uploads/2022/02/coco-ralado-medio-tiny.jpg"
        ),    Question(
            "Qual é o doce típico do estado de Minas Gerais feito com queijo e goiabada?",
            listOf("Romeu e Julieta", "Quindim", "Pudim", "Cocada"),
            "Romeu e Julieta",
            "https://direcional.com.br/wp-content/uploads/2021/08/minas-gerais-3.jpg"
        ),
        Question(
            "Qual é o doce feito com açúcar mascavo, manteiga e farinha de trigo, tradicional do estado do Paraná?",
            listOf("Bolo de rolo", "Cuscuz", "Banoffee", "Cuca"),
            "Cuca",
            "https://www.viagensecaminhos.com/wp-content/uploads/2022/01/catedral-maringa-pr.jpg"
        ),
        Question(
            "Qual é o doce brasileiro feito com banana, leite condensado, creme de leite e biscoito maisena?",
            listOf("Banoffee", "Quindim", "Pavê", "Brigadeiro"),
            "Banoffee",
            "https://minhasaude.proteste.org.br/wp-content/uploads/2022/07/bananas.jpg"
        ),
        Question(
            "Qual é o doce brasileiro de origem indígena, feito com mandioca, açúcar e coco ralado?",
            listOf("Manjar", "Mandioca frita", "Quindim", "Beiju"),
            "Beiju",
            "https://blog.123milhas.com/wp-content/uploads/2023/02/Dia-Nacional-de-Luta-dos-Povos-Indigenas-conexao123-scaled.jpg"
        ),
        Question(
            "Qual é o doce brasileiro feito com amendoim, açúcar e rapadura?",
            listOf("Manjar", "Paçoca", "Canjica", "Quindim"),
            "Paçoca",
            "https://images.tcdn.com.br/img/img_prod/639993/rapadura_92_1_20180815102630.jpg"
        ),
        Question(
            "Qual é o doce típico da culinária baiana, feito com coco ralado, leite condensado e gemas de ovo?",
            listOf("Pavê", "Quindim", "Bolo de rolo", "Cocada"),
            "Cocada",
            "https://media.tacdn.com/media/attractions-splice-spp-674x446/06/dd/dd/0b.jpg"
        )

    )


}

