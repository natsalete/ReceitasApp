package com.example.receitasapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

// Modelo de dados
data class Receita(
    val id: Int,
    val nome: String,
    val categoria: String,
    val tempoPreparo: String,
    val porcoes: Int,
    val dificuldade: String,
    val imagemUrl: String,
    val ingredientes: List<String>,
    val modoPreparo: List<String>,
    val calorias: String
)

// Repository com dados estáticos
object ReceitaRepository {
    val receitas = listOf(
        Receita(
            id = 1,
            nome = "Panqueca Americana",
            categoria = "Café da Manhã",
            tempoPreparo = "20 min",
            porcoes = 4,
            dificuldade = "Fácil",
            imagemUrl = "🥞",
            ingredientes = listOf(
                "2 xícaras de farinha de trigo",
                "2 colheres de sopa de açúcar",
                "2 colheres de chá de fermento em pó",
                "1/2 colher de chá de sal",
                "2 ovos",
                "1 e 3/4 xícara de leite",
                "1/4 xícara de manteiga derretida"
            ),
            modoPreparo = listOf(
                "Em uma tigela, misture a farinha, açúcar, fermento e sal",
                "Em outra tigela, bata os ovos e adicione o leite e a manteiga",
                "Combine os ingredientes secos com os líquidos",
                "Aqueça uma frigideira antiaderente em fogo médio",
                "Despeje porções da massa e cozinhe até formar bolhas",
                "Vire e cozinhe por mais 1-2 minutos",
                "Sirva com mel, frutas ou xarope de bordo"
            ),
            calorias = "320 kcal"
        ),
        Receita(
            id = 2,
            nome = "Salmão Grelhado",
            categoria = "Almoço",
            tempoPreparo = "25 min",
            porcoes = 2,
            dificuldade = "Média",
            imagemUrl = "🐟",
            ingredientes = listOf(
                "2 filés de salmão",
                "2 colheres de azeite",
                "Suco de 1 limão",
                "2 dentes de alho picados",
                "Sal e pimenta a gosto",
                "Ervas finas (tomilho, alecrim)",
                "Legumes para acompanhamento"
            ),
            modoPreparo = listOf(
                "Tempere o salmão com sal, pimenta, alho e limão",
                "Deixe marinar por 15 minutos",
                "Aqueça o azeite em uma frigideira",
                "Grelhe o salmão por 4-5 minutos de cada lado",
                "Prepare os legumes grelhados separadamente",
                "Sirva quente com arroz ou batatas"
            ),
            calorias = "380 kcal"
        ),
        Receita(
            id = 3,
            nome = "Risoto de Cogumelos",
            categoria = "Jantar",
            tempoPreparo = "40 min",
            porcoes = 4,
            dificuldade = "Média",
            imagemUrl = "🍄",
            ingredientes = listOf(
                "2 xícaras de arroz arbóreo",
                "300g de cogumelos variados",
                "1 cebola picada",
                "1 litro de caldo de legumes",
                "1/2 xícara de vinho branco",
                "50g de manteiga",
                "1/2 xícara de queijo parmesão",
                "Sal e pimenta a gosto"
            ),
            modoPreparo = listOf(
                "Refogue a cebola na manteiga até dourar",
                "Adicione os cogumelos e cozinhe por 5 minutos",
                "Acrescente o arroz e mexa por 2 minutos",
                "Adicione o vinho e deixe evaporar",
                "Vá adicionando o caldo quente aos poucos",
                "Mexa constantemente até o arroz ficar cremoso",
                "Finalize com parmesão, sal e pimenta"
            ),
            calorias = "420 kcal"
        ),
        Receita(
            id = 4,
            nome = "Brownie de Chocolate",
            categoria = "Sobremesa",
            tempoPreparo = "35 min",
            porcoes = 8,
            dificuldade = "Fácil",
            imagemUrl = "🍫",
            ingredientes = listOf(
                "200g de chocolate meio amargo",
                "150g de manteiga",
                "3 ovos",
                "1 xícara de açúcar",
                "1/2 xícara de farinha de trigo",
                "1 colher de chá de essência de baunilha",
                "Pitada de sal"
            ),
            modoPreparo = listOf(
                "Derreta o chocolate com a manteiga em banho-maria",
                "Bata os ovos com o açúcar até ficar cremoso",
                "Adicione o chocolate derretido e a baunilha",
                "Incorpore delicadamente a farinha e o sal",
                "Despeje em forma untada",
                "Asse a 180°C por 25 minutos",
                "Deixe esfriar e corte em quadrados"
            ),
            calorias = "280 kcal"
        ),
        Receita(
            id = 5,
            nome = "Smoothie Bowl",
            categoria = "Café da Manhã",
            tempoPreparo = "10 min",
            porcoes = 1,
            dificuldade = "Fácil",
            imagemUrl = "🥣",
            ingredientes = listOf(
                "2 bananas congeladas",
                "1 xícara de frutas vermelhas",
                "1/2 xícara de leite ou bebida vegetal",
                "1 colher de mel",
                "Granola para decorar",
                "Frutas frescas para decorar",
                "Sementes de chia"
            ),
            modoPreparo = listOf(
                "Bata as bananas congeladas com as frutas vermelhas",
                "Adicione o leite aos poucos até obter consistência cremosa",
                "Despeje em uma tigela",
                "Decore com granola, frutas frescas e chia",
                "Sirva imediatamente"
            ),
            calorias = "250 kcal"
        ),
        Receita(
            id = 6,
            nome = "Lasanha à Bolonhesa",
            categoria = "Almoço",
            tempoPreparo = "60 min",
            porcoes = 6,
            dificuldade = "Média",
            imagemUrl = "🍝",
            ingredientes = listOf(
                "500g de massa para lasanha",
                "500g de carne moída",
                "1 cebola picada",
                "3 dentes de alho",
                "700ml de molho de tomate",
                "500ml de molho branco",
                "200g de queijo mussarela",
                "Queijo parmesão ralado"
            ),
            modoPreparo = listOf(
                "Refogue a carne com cebola e alho",
                "Adicione o molho de tomate e temperos",
                "Em um refratário, alterne camadas de molho, massa e queijo",
                "Finalize com molho branco e queijo",
                "Asse em forno pré-aquecido a 200°C por 40 minutos",
                "Deixe descansar por 10 minutos antes de servir"
            ),
            calorias = "480 kcal"
        ),
        Receita(
            id = 7,
            nome = "Salada Caesar",
            categoria = "Jantar",
            tempoPreparo = "15 min",
            porcoes = 2,
            dificuldade = "Fácil",
            imagemUrl = "🥗",
            ingredientes = listOf(
                "1 pé de alface romana",
                "200g de peito de frango grelhado",
                "Croutons",
                "Lascas de parmesão",
                "Molho Caesar",
                "Suco de limão",
                "Azeite extra virgem"
            ),
            modoPreparo = listOf(
                "Lave e rasgue as folhas de alface",
                "Corte o frango em tiras",
                "Misture a alface com o molho Caesar",
                "Adicione o frango e os croutons",
                "Finalize com parmesão e um fio de azeite",
                "Sirva imediatamente"
            ),
            calorias = "320 kcal"
        ),
        Receita(
            id = 8,
            nome = "Pudim de Leite",
            categoria = "Sobremesa",
            tempoPreparo = "90 min",
            porcoes = 8,
            dificuldade = "Média",
            imagemUrl = "🍮",
            ingredientes = listOf(
                "1 lata de leite condensado",
                "2 latas de leite (use a lata do condensado)",
                "3 ovos",
                "1 xícara de açúcar para a calda",
                "1 colher de essência de baunilha"
            ),
            modoPreparo = listOf(
                "Faça a calda com o açúcar em forma de pudim",
                "Bata no liquidificador o leite condensado, leite, ovos e baunilha",
                "Despeje sobre a calda",
                "Asse em banho-maria a 180°C por 60 minutos",
                "Deixe esfriar e leve à geladeira por 4 horas",
                "Desenforme e sirva gelado"
            ),
            calorias = "310 kcal"
        )
    )

    fun getReceitaById(id: Int): Receita? {
        return receitas.find { it.id == id }
    }

    fun getCategorias(): List<String> {
        return receitas.map { it.categoria }.distinct()
    }
}

// ViewModel
class ReceitasViewModel {
    private val _receitas = mutableStateOf(ReceitaRepository.receitas)
    val receitas: State<List<Receita>> = _receitas

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private val _categoriaFiltro = mutableStateOf("Todas")
    val categoriaFiltro: State<String> = _categoriaFiltro

    private val _favoritos = mutableStateOf(setOf<Int>())
    val favoritos: State<Set<Int>> = _favoritos

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        aplicarFiltros()
    }

    fun updateCategoriaFiltro(categoria: String) {
        _categoriaFiltro.value = categoria
        aplicarFiltros()
    }

    fun toggleFavorito(receitaId: Int) {
        val favs = _favoritos.value.toMutableSet()
        if (favs.contains(receitaId)) {
            favs.remove(receitaId)
        } else {
            favs.add(receitaId)
        }
        _favoritos.value = favs
    }

    fun isFavorito(receitaId: Int): Boolean {
        return _favoritos.value.contains(receitaId)
    }

    private fun aplicarFiltros() {
        var resultado = ReceitaRepository.receitas

        if (_categoriaFiltro.value != "Todas") {
            resultado = resultado.filter { it.categoria == _categoriaFiltro.value }
        }

        if (_searchQuery.value.isNotBlank()) {
            resultado = resultado.filter {
                it.nome.contains(_searchQuery.value, ignoreCase = true) ||
                        it.ingredientes.any { ing -> ing.contains(_searchQuery.value, ignoreCase = true) }
            }
        }

        _receitas.value = resultado
    }
}

// Activity Principal
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReceitasAppTheme {
                ReceitasApp()
            }
        }
    }
}

// Tema do App
@Composable
fun ReceitasAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFFFF6B35),
            secondary = Color(0xFFF7931E),
            tertiary = Color(0xFF4ECDC4),
            background = Color(0xFFF8F9FA),
            surface = Color.White,
            onPrimary = Color.White,
            onSecondary = Color.White,
            onBackground = Color(0xFF1A1A1A),
            onSurface = Color(0xFF1A1A1A)
        ),
        content = content
    )
}

// Navegação Principal
@Composable
fun ReceitasApp() {
    val navController = rememberNavController()
    val viewModel = remember { ReceitasViewModel() }

    NavHost(navController = navController, startDestination = "lista") {
        composable("lista") {
            TelaListaReceitas(navController, viewModel)
        }
        composable(
            route = "detalhes/{receitaId}",
            arguments = listOf(navArgument("receitaId") { type = NavType.IntType })
        ) { backStackEntry ->
            val receitaId = backStackEntry.arguments?.getInt("receitaId") ?: return@composable
            TelaDetalhesReceita(navController, receitaId, viewModel)
        }
    }
}

// Tela de Lista
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaListaReceitas(navController: NavController, viewModel: ReceitasViewModel) {
    val receitas by viewModel.receitas
    val searchQuery by viewModel.searchQuery
    val categoriaFiltro by viewModel.categoriaFiltro
    val categorias = remember { listOf("Todas") + ReceitaRepository.getCategorias() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Minhas Receitas",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Campo de busca
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.updateSearchQuery(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Buscar receitas...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Buscar")
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { viewModel.updateSearchQuery("") }) {
                            Icon(Icons.Default.Clear, contentDescription = "Limpar")
                        }
                    }
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = Color.LightGray
                )
            )

            // Filtro de categorias
            LazyRow(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categorias) { categoria ->
                    FilterChip(
                        selected = categoriaFiltro == categoria,
                        onClick = { viewModel.updateCategoriaFiltro(categoria) },
                        label = { Text(categoria) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            // Lista de receitas
            if (receitas.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "🔍",
                            fontSize = 48.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "Nenhuma receita encontrada",
                            fontSize = 18.sp,
                            color = Color.Gray
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(receitas) { receita ->
                        ReceitaCard(
                            receita = receita,
                            onClick = { navController.navigate("detalhes/${receita.id}") },
                            isFavorito = viewModel.isFavorito(receita.id),
                            onFavoritoClick = { viewModel.toggleFavorito(receita.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ReceitaCard(
    receita: Receita,
    onClick: () -> Unit,
    isFavorito: Boolean,
    onFavoritoClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Emoji como imagem
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                                MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = receita.imagemUrl,
                    fontSize = 40.sp
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Informações
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = receita.nome,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.DateRange,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        receita.tempoPreparo,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        receita.dificuldade,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                AssistChip(
                    onClick = { },
                    label = { Text(receita.categoria, fontSize = 12.sp) },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f),
                        labelColor = MaterialTheme.colorScheme.tertiary
                    )
                )
            }

            // Botão de favorito
            IconButton(onClick = onFavoritoClick) {
                Icon(
                    if (isFavorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favoritar",
                    tint = if (isFavorito) Color.Red else Color.Gray
                )
            }
        }
    }
}

// Tela de Detalhes
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaDetalhesReceita(
    navController: NavController,
    receitaId: Int,
    viewModel: ReceitasViewModel
) {
    val receita = ReceitaRepository.getReceitaById(receitaId) ?: return
    val isFavorito = viewModel.isFavorito(receitaId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes da Receita", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Voltar",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.toggleFavorito(receitaId) }) {
                        Icon(
                            if (isFavorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favoritar",
                            tint = if (isFavorito) Color.Red else Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Header com imagem
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.colorScheme.secondary
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = receita.imagemUrl,
                        fontSize = 120.sp
                    )
                }
            }

            // Título e informações básicas
            item {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = receita.nome,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        InfoChip(
                            icon = Icons.Default.DateRange,
                            label = receita.tempoPreparo
                        )
                        InfoChip(
                            icon = Icons.Default.Person,
                            label = "${receita.porcoes} porções"
                        )
                        InfoChip(
                            icon = Icons.Default.Star,
                            label = receita.dificuldade
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f)
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Info,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.tertiary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Calorias por porção: ${receita.calorias}",
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            // Ingredientes
            item {
                SectionTitle("Ingredientes")
            }

            items(receita.ingredientes) { ingrediente ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = ingrediente,
                        fontSize = 16.sp
                    )
                }
            }

            // Modo de Preparo
            item {
                Spacer(modifier = Modifier.height(8.dp))
                SectionTitle("Modo de Preparo")
            }

            items(receita.modoPreparo.size) { index ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${index + 1}",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = receita.modoPreparo[index],
                        fontSize = 16.sp,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
    )
}

@Composable
fun InfoChip(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}