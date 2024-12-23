package com.tta.core_database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tta.core_database.TABLE_NAME
import java.time.LocalDate

@Entity(tableName = TABLE_NAME)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    // Local ID (tức là trong room Db)
    //    @Expose(serialize = false, deserialize = false)
    val localId: Int = 0,
    // Mặc định sẽ == null vì chưa đồng bọo lên server khi nào init app bắt buộc phải có thì mới update lên server hay gì đó được
    val userRoomId: Int? = null,

    //User Server id (ID này sẽ từ server trả về và cần push lên để đồng bộ)
    val userServerId: Int? = null,

    // Thông tin task ------------
    var nameTask: String,
    var descTask: String? = null,
    // TODO: Tạm thời để vậy chưa version 1 sẽ chưa có truyển ảnh vì nó nặng :)))
    var imgTask: String? = null,

    // kết nối với Category Entity
    var category: Int? = null,
    // Nếu để kiểu Long thì sẽ bị sai khi người dùng sử dụng múi giờ khác nhau nên khi nào làm server sẽ để kiểu LocalDate để ép kiểu về DateTimeFormatter.ISO_LOCAL_DATE
    var date : LocalDate,
    // Theo Priority.kt thì mức độ ưu tiên thấp nhất là 10 nên để mặc định thấp nhất
    var priority: Int? = 10,

    // Hoàn thành task hay chưa hoàn thành
    var isComplete: Boolean = false,
    // Dùng để đồng bộ lên server nếu lastUpdate = 0 là chưa đồng bộ, khi nào server đồng bộ sẽ cần
    var lastUpdated : Long? = null,
    // Xóa mềm chứ không dùng delete Task không room, khi xóa sẽ để == true để server và chuyển vào mục task đã xóa sau 30 ko dùng nữa thì mới xóa cả trên rooom và server
    var deleted : Boolean = false,
    //    @Expose(serialize = false, deserialize = false)
    // Sau khi update mục gì đó đều cần chuyển trạng thái thành true để, nếu là true thì cập nhật lên server
    var needSync : Boolean = false,
)

/*
@Expose(serialize = false, deserialize = false) nghĩa là không truyển lên server chỉ để ở trong room db
 */