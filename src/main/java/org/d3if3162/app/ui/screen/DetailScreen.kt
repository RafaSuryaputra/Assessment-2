package org.d3if3162.app.ui.screen

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3162.app.R
import org.d3if3162.app.database.KaryawanDb
import org.d3if3162.app.ui.theme.AppTheme
import org.d3if3162.app.util.ViewModelFactory

const val KEY_ID_KARYAWAN =  "idKaryawan"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, id: Long? = null){
    val context = LocalContext.current
    val db = KaryawanDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    var nama by remember { mutableStateOf("") }
    var dataDiri by remember { mutableStateOf("") }

    var showDialog by remember { mutableStateOf(false) }

    val radioOptions = listOf(
        stringResource(id = R.string.alpha),
        stringResource(id = R.string.hadir),
        stringResource(id = R.string.izin),
        stringResource(id = R.string.sakit)
    )

    var selectedAbsen by rememberSaveable { mutableStateOf(radioOptions[0]) }

    LaunchedEffect(true) {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getCatatan(id) ?: return@LaunchedEffect
        nama = data.nama
        dataDiri = data.data_diri
        selectedAbsen = data.absen
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack()}) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.kembali),
                        tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                title = {
                    if (id == null)
                        Text(text = stringResource(id = R.string.tambah_karyawan))
                    else
                        Text(text = stringResource(id = R.string.edit_karyawan))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(0xFFF4CC53), // Warna container
                    titleContentColor = Color.White // Warna konten judul
                ),
                actions = {
                    IconButton(onClick = {
                            if (nama == "" || dataDiri == "") {
                                Toast.makeText(context, R.string.invalid, Toast.LENGTH_LONG).show()
                                return@IconButton
                            }
                            if (id == null) {
                                viewModel.insert(nama, dataDiri, selectedAbsen)
                            } else {
                                viewModel.update(id, nama, dataDiri, selectedAbsen)
                            }
                        navController.popBackStack()}) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = stringResource(id = R.string.simpan),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    if (id != null) {
                        DeleteAction {showDialog = true }

                        DisplayAlertDialog(
                            openDialog = showDialog,
                            onDismissRequest = {showDialog = false}) {
                            showDialog = false
                            viewModel.delete(id)
                            navController.popBackStack()
                        }
                    }
                }
            )
        }
    ) { padding ->
        FormKaryawan(
            title = nama,
            onTitleChange = {nama = it},
            desc = dataDiri,
            onDescChange = {dataDiri     = it},
            pilihanAbsen = selectedAbsen,
            absenBerubah = {selectedAbsen = it},
            radioOpsi = radioOptions,
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
fun DeleteAction(delete: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = {expanded = true}) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = R.string.lainnya),
            tint = MaterialTheme.colorScheme.primary
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {expanded = false}
        ) {
            DropdownMenuItem(
                text = {
                       Text(text = stringResource(id = R.string.hapus))
                },
                onClick = {
                expanded = false
                delete()
                }
            )
        }
    }
}
@Composable
fun FormKaryawan(
    title: String, onTitleChange: (String) -> Unit,
    desc: String, onDescChange: (String) -> Unit,
    pilihanAbsen:String,absenBerubah:(String)->Unit,
    radioOpsi: List<String>,
    modifier: Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "logo",
        modifier = modifier.fillMaxSize().padding(16.dp),
    )
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = {onTitleChange(it)},
            label = { Text(text = stringResource(id = R.string.nama))},
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = desc,
            onValueChange = {onDescChange(it)},
            label = { Text(text = stringResource(id = R.string.data_diri))},
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words
            ),
            modifier = Modifier.fillMaxWidth().size(300.dp)
        )
        Column(
            modifier = Modifier
                .padding(top = 6.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
        ){
            radioOpsi.forEach { text -> absenOption(
                label = text,
                isSelected = pilihanAbsen == text,
                modifier = Modifier
                    .selectable(
                        selected = pilihanAbsen == text,
                        onClick = { absenBerubah(text) },
                        role = Role.RadioButton
                    )
                    .padding(16.dp)
                    .fillMaxWidth()
            )
            }
        }
    }
}

@Composable
fun absenOption (label:String, isSelected: Boolean, modifier: Modifier ){
    Row (
        modifier = modifier,

        ){
        RadioButton(selected = isSelected, onClick = null)
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}
@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DetailPreview() {
    AppTheme {
        DetailScreen(rememberNavController())
    }
}