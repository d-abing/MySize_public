package com.aube.mysize.presentation.ui.screens.closet.clothes_detail

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aube.mysize.R
import com.aube.mysize.domain.model.clothes.Visibility
import com.aube.mysize.domain.model.size.AccessorySize
import com.aube.mysize.domain.model.size.BottomSize
import com.aube.mysize.domain.model.size.OnePieceSize
import com.aube.mysize.domain.model.size.OuterSize
import com.aube.mysize.domain.model.size.ShoeSize
import com.aube.mysize.domain.model.size.TopSize
import com.aube.mysize.domain.model.size.resolveCategory
import com.aube.mysize.presentation.constants.formatAccessorySize
import com.aube.mysize.presentation.constants.formatBottomSize
import com.aube.mysize.presentation.constants.formatOnePieceSize
import com.aube.mysize.presentation.constants.formatOuterSize
import com.aube.mysize.presentation.constants.formatShoeSize
import com.aube.mysize.presentation.constants.formatTopSize
import com.aube.mysize.presentation.model.size.SizeSummaryItem
import com.aube.mysize.presentation.viewmodel.clothes.ClothesDetailViewModel
import com.aube.mysize.presentation.viewmodel.clothes.MyClothesViewModel
import com.aube.mysize.presentation.viewmodel.clothes.OtherClothesViewModel
import com.aube.mysize.presentation.viewmodel.size.SizeViewModel
import com.aube.mysize.presentation.viewmodel.user.ProfileViewModel
import com.aube.mysize.presentation.viewmodel.user.ReportViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import java.time.format.DateTimeFormatter

@Composable
fun ClothesDetailScreen(
    reportViewModel: ReportViewModel = hiltViewModel(),
    clothesDetailViewModel: ClothesDetailViewModel,
    myClothesViewModel: MyClothesViewModel,
    otherClothesViewModel: OtherClothesViewModel,
    sizeViewModel: SizeViewModel,
    profileViewModel: ProfileViewModel,
    navController: NavController,
    clothesId: String,
    onDelete: () -> Unit,
    onEdit: () -> Unit = {},
    onProfileClick: (String) -> Unit,
) {
    val clothesDetail by clothesDetailViewModel.clothesDetail.collectAsState()
    val savedIds by sizeViewModel.savedSizeIds.collectAsState()
    val isConnected by profileViewModel.isConnected.collectAsState()

    val clothes = clothesDetail?.clothes
    val nickname = clothesDetail?.nickname.orEmpty()

    var showDeleteDialog by remember { mutableStateOf(false) }
    var showReportDialog by remember { mutableStateOf(false) }
    var summaryItems by remember { mutableStateOf<List<SizeSummaryItem>>(emptyList()) }

    val handle = navController.currentBackStackEntry?.savedStateHandle
    val updatedImageUrl = handle?.get<String>("updated_image_url")
    val isBlocked = handle?.get<Boolean>("is_blocked")
    var isReported = handle?.get<Boolean>("is_reported")

    if (updatedImageUrl != null) {
        LaunchedEffect(updatedImageUrl) {
            clothes?.imageUrl = updatedImageUrl
            handle.remove<String>("updated_image_url")
        }
    }

    LaunchedEffect(clothesId) {
        clothesDetailViewModel.clearClothesDetailState()
        clothesDetailViewModel.getById(clothesId)
    }

    BackHandler {
        navController.previousBackStackEntry?.savedStateHandle?.set("is_blocked", isBlocked)
        navController.popBackStack()
    }

    clothes?.let {
        val isOwner = clothes.createUserId == Firebase.auth.currentUser?.uid

        val formattedDate = remember(clothes) {
            buildString {
                append(clothes.createdAt.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")))
                if (clothes.updatedAt != null) append(" (수정됨)")
            }
        }

        LaunchedEffect(clothes) {
            sizeViewModel.getSizesByLinkedGroups(clothes.linkedSizes, clothes.createUserId)
                .collect { sizes ->
                    summaryItems = sizes.mapNotNull { size ->
                        val summary = when (size) {
                            is TopSize -> formatTopSize(size, clothes.memoVisibility)
                            is BottomSize -> formatBottomSize(size, clothes.memoVisibility)
                            is OuterSize -> formatOuterSize(size, clothes.memoVisibility)
                            is OnePieceSize -> formatOnePieceSize(size, clothes.memoVisibility)
                            is ShoeSize -> formatShoeSize(size, clothes.memoVisibility)
                            is AccessorySize -> formatAccessorySize(size, clothes.memoVisibility)
                            else -> null
                        }
                        summary?.let { SizeSummaryItem(it, size.resolveCategory(), size.id) }
                    }
                }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            ClothesDetailHeader(
                nickname = nickname,
                dateText = formattedDate,
                imageUrl = clothes.createUserProfileImageUrl,
                isOwner = isOwner,
                isConnected = isConnected,
                onReport = { showReportDialog = true },
                onDelete = { showDeleteDialog = true },
                onEdit = onEdit,
                onProfileClick = { onProfileClick(clothes.createUserId) }
            )

            ClothesImage(clothes.imageUrl)
            ClothesPrivacyIndicators(clothes)
            ClothesMemo(clothes.memo)
            ClothesTags(clothes.tags)

            if (
                clothes.visibility == Visibility.PUBLIC &&
                !isOwner &&
                clothes.sharedBodyFields.isNotEmpty() &&
                clothes.bodySize != null
            ) {
                BodySizeCardSection(clothes)
            }

            SizeSummaryCardList(
                items = summaryItems,
                dominantColor = clothes.dominantColor,
                isOwner = isOwner,
                savedIds = savedIds,
                onSave = sizeViewModel::saveSizeById,
                onUnsave = sizeViewModel::deleteSizeById
            )
        }

        ClothesDetailDialogs(
            showDeleteDialog = showDeleteDialog,
            showReportDialog = showReportDialog,
            onDismissDelete = { showDeleteDialog = false },
            onDismissReport = { showReportDialog = false },
            onDeleteConfirm = {
                showDeleteDialog = false
                myClothesViewModel.delete(clothes)
                myClothesViewModel.loadMyClothesList()
                onDelete()
            },
            onReportConfirm = { reason ->
                showReportDialog = false
                reportViewModel.reportClothes(clothesId, reason.name)
                otherClothesViewModel.refreshReportedClothes()
                otherClothesViewModel.refreshOthersClothesList()
                isReported = true
                navController.previousBackStackEntry?.savedStateHandle?.set("is_reported", isReported)
                navController.popBackStack()
            }
        )
    } ?:

    isBlocked?.let {
        if (isBlocked == true) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.text_blocked_user_message),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }

    isReported?.let {
        if (isReported == true) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.text_reported_user_message),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
