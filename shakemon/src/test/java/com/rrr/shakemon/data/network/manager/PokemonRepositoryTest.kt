package com.rrr.shakemon.data.network.manager

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rrr.shakemon.data.PokemonRepository
import com.rrr.shakemon.data.Util
import com.rrr.shakemon.data.network.model.PokemonDescription
import com.rrr.shakemon.data.network.model.PokemonSprite
import com.rrr.shakemon.data.network.model.Translation
import com.rrr.shakemon.data.network.service.PokemonApi
import com.rrr.shakemon.data.network.service.TranslationsApi
import com.rrr.shakemon.utils.MockResponse
import com.rrr.shakemon.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.lang.Exception

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PokemonRepositoryTest {
    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()
    @get:Rule
    val coroutinesTestRule: TestCoroutineRule = TestCoroutineRule()
    @Mock
    lateinit var translationsApi: TranslationsApi
    @Mock
    lateinit var pokemonApi: PokemonApi
    private lateinit var pokemonRepository: PokemonRepository
    private lateinit var autoCloseable: AutoCloseable

    @Before
    internal fun setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this)
        pokemonRepository = PokemonRepository(
            pokemonApi = pokemonApi, translationsApi = translationsApi
        )
    }

    @After
    internal fun tear() {
        autoCloseable.close()
    }

    @Test
    fun `test description for unknown pokemon name`() {
        coroutinesTestRule.runBlockingTest {
            try {
                pokemonRepository.getPokemonShakespeareDescription("abc")
                Assert.assertTrue(false)
            }catch (exception: Exception){
                Assert.assertTrue(exception != null)
            }
        }
    }

    @Test
    fun `test description for known pokemon name`() {
        // Test preparation
        val favour_text = "A strange seed was planted on its back at birth.\\fThe plant sprouts and grows with this POKéMON."
        val mockTranslation = MockResponse("bulbasaur_translation").mockObject(Translation::class.java)
        coroutinesTestRule.runBlockingTest {
            Mockito.`when`(pokemonApi.getPokemonDescription(Util.POKEMON_DESCRIPTION_API + "bulbasaur"))
                .thenReturn(MockResponse("bulbasaur_species").mockObject(PokemonDescription::class.java))
            Mockito.`when`(translationsApi.getShakespeareText(favour_text))
                .thenReturn(mockTranslation)
            // test
            try {
                    val actualResponse = pokemonRepository.getPokemonShakespeareDescription("bulbasaur")
                    Assert.assertEquals(mockTranslation.contents.translated, actualResponse)
            }catch (exception: Exception){
                Assert.assertTrue(false)
            }
        }
    }

    @Test
    fun `test sprite for unknown pokemon name`() {
        coroutinesTestRule.runBlockingTest {
            try {
                pokemonRepository.getPokemonShakespeareDescription("abc")
            }catch (exception: Exception){
                Assert.assertTrue(exception != null)
            }
        }
    }

    @Test
    fun `test sprite for known pokemon name`() {
        // Test preparation
        val mockSprite = MockResponse("bulbasaur_sprite").mockObject(PokemonSprite::class.java)
        coroutinesTestRule.runBlockingTest {
            Mockito.`when`(pokemonApi.getPokemonSprite("https://pokeapi.co/api/v2/pokemon/bulbasaur"))
                .thenReturn(MockResponse("bulbasaur_sprite").mockObject(PokemonSprite::class.java))
            // test
            try {
                val actualResponse = pokemonRepository.getPokemonSprite("https://pokeapi.co/api/v2/pokemon/bulbasaur")
                val expected = mockSprite.sprite.other.officialArtwork?.frontDefault
                    ?:mockSprite.sprite.frontDefault
                Assert.assertEquals(expected, actualResponse)
            }catch (exception: Exception){
                Assert.assertTrue(false)
            }
        }
    }
}