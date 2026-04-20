package com.dass.ims.ui.search

import androidx.lifecycle.ViewModel
import com.dass.ims.data.repository.StudentRepository
import com.dass.ims.data.repository.BatchRepository
import com.dass.ims.data.repository.SubjectRepository

class SearchViewModel(
    val students: StudentRepository,
    val batches: BatchRepository,
    val subjects: SubjectRepository
) : ViewModel()
