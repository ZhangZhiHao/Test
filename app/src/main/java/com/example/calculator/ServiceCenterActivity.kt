package com.example.calculator

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ServiceCenterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_center)

        val sectionList = findViewById<RecyclerView>(R.id.sectionList)
        sectionList.layoutManager = LinearLayoutManager(this)
        sectionList.adapter = ServiceSectionAdapter(buildSections()) { item ->
            Toast.makeText(this, "点击了：${item.name}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun buildSections(): List<ServiceSection> = listOf(
        ServiceSection(
            "常用功能",
            listOf(
                ServiceItem("智家商城", android.R.drawable.ic_menu_gallery),
                ServiceItem("联通手机", android.R.drawable.ic_menu_call),
                ServiceItem("安全管家", android.R.drawable.ic_lock_lock),
                ServiceItem("虚拟体验", android.R.drawable.ic_menu_compass),
                ServiceItem("随身固话", android.R.drawable.ic_btn_speak_now),
                ServiceItem("联通沃邮箱", android.R.drawable.ic_dialog_email),
                ServiceItem("视频彩通", android.R.drawable.ic_media_play)
            )
        ),
        ServiceSection(
            "网络服务",
            listOf(
                ServiceItem("WIFI 测试", android.R.drawable.ic_menu_search),
                ServiceItem("宽带账号", android.R.drawable.ic_menu_myplaces),
                ServiceItem("WIFI 覆盖", android.R.drawable.ic_menu_mapmode),
                ServiceItem("WIFI 评估", android.R.drawable.ic_menu_manage),
                ServiceItem("WIFI 漫游", android.R.drawable.ic_popup_sync),
                ServiceItem("一键保修", android.R.drawable.ic_menu_edit),
                ServiceItem("查找设备", android.R.drawable.ic_menu_mylocation),
                ServiceItem("网络宽带", android.R.drawable.ic_menu_upload)
            )
        ),
        ServiceSection(
            "智慧家庭",
            listOf(
                ServiceItem("智家商城", android.R.drawable.ic_menu_gallery),
                ServiceItem("安全管家", android.R.drawable.ic_lock_lock),
                ServiceItem("宠AI", android.R.drawable.ic_menu_help),
                ServiceItem("产品百科", android.R.drawable.ic_menu_info_details),
                ServiceItem("添加设备", android.R.drawable.ic_input_add),
                ServiceItem("全屋设备", android.R.drawable.ic_menu_slideshow),
                ServiceItem("联通U爱", android.R.drawable.ic_menu_share),
                ServiceItem("VR体验馆", android.R.drawable.ic_menu_view)
            )
        ),
        ServiceSection(
            "生活娱乐",
            listOf(
                ServiceItem("联通沃邮箱", android.R.drawable.ic_dialog_email),
                ServiceItem("联通云手机", android.R.drawable.ic_menu_call),
                ServiceItem("随身固话", android.R.drawable.ic_btn_speak_now),
                ServiceItem("联通爱听", android.R.drawable.ic_media_ff),
                ServiceItem("通信账单", android.R.drawable.ic_menu_agenda),
                ServiceItem("联通固话", android.R.drawable.ic_menu_call),
                ServiceItem("充值缴费", android.R.drawable.ic_menu_send)
            )
        ),
        ServiceSection(
            "健康服务",
            listOf(
                ServiceItem("健康到家", android.R.drawable.presence_online),
                ServiceItem("在线问诊", android.R.drawable.ic_menu_info_details),
                ServiceItem("用药提醒", android.R.drawable.ic_menu_recent_history),
                ServiceItem("告警给家人", android.R.drawable.ic_menu_directions),
                ServiceItem("健康随访", android.R.drawable.ic_menu_week),
                ServiceItem("健康档案", android.R.drawable.ic_menu_edit)
            )
        ),
        ServiceSection(
            "活动专区",
            listOf(
                ServiceItem("热门活动", android.R.drawable.star_big_on),
                ServiceItem("云存兑换", android.R.drawable.ic_menu_rotate)
            )
        )
    )
}

data class ServiceSection(val title: String, val items: List<ServiceItem>)

data class ServiceItem(val name: String, val iconRes: Int)

class ServiceSectionAdapter(
    private val sections: List<ServiceSection>,
    private val onItemClick: (ServiceItem) -> Unit
) : RecyclerView.Adapter<ServiceSectionViewHolder>() {

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): ServiceSectionViewHolder {
        val view = android.view.LayoutInflater.from(parent.context)
            .inflate(R.layout.item_service_section, parent, false)
        return ServiceSectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServiceSectionViewHolder, position: Int) {
        holder.bind(sections[position], onItemClick)
    }

    override fun getItemCount(): Int = sections.size
}

class ServiceSectionViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
    private val title = itemView.findViewById<android.widget.TextView>(R.id.sectionTitle)
    private val grid = itemView.findViewById<RecyclerView>(R.id.functionGrid)

    fun bind(section: ServiceSection, onItemClick: (ServiceItem) -> Unit) {
        title.text = section.title
        grid.layoutManager = GridLayoutManager(itemView.context, 4)
        grid.adapter = ServiceFunctionAdapter(section.items, onItemClick)
    }
}

class ServiceFunctionAdapter(
    private val items: List<ServiceItem>,
    private val onItemClick: (ServiceItem) -> Unit
) : RecyclerView.Adapter<ServiceFunctionViewHolder>() {

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): ServiceFunctionViewHolder {
        val view = android.view.LayoutInflater.from(parent.context)
            .inflate(R.layout.item_service_function, parent, false)
        return ServiceFunctionViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServiceFunctionViewHolder, position: Int) {
        holder.bind(items[position], onItemClick)
    }

    override fun getItemCount(): Int = items.size
}

class ServiceFunctionViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
    private val icon = itemView.findViewById<android.widget.ImageView>(R.id.functionIcon)
    private val name = itemView.findViewById<android.widget.TextView>(R.id.functionName)

    fun bind(item: ServiceItem, onItemClick: (ServiceItem) -> Unit) {
        icon.setImageResource(item.iconRes)
        name.text = item.name
        itemView.setOnClickListener { onItemClick(item) }
    }
}
