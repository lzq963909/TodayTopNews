package xunqaing.bwie.com.todaytopnews.newsdrag.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * ITEM的对应可序化队列属性
 *  */
@Table(name = "MyChannelItem1")
public class ChannelItem implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -6465237897027410019L;
	/**
	 * 栏目对应ID
	 *  */
	@Column(name = "id",isId = true,autoGen = true)
	public Integer id;
	/**
	 * 栏目对应NAME
	 *  */
	@Column(name = "name")
	public String name;
	/**
	 * 栏目在整体中的排序顺序  rank
	 *  */
	@Column(name = "orderId")
	public Integer orderId;
	/**
	 * 栏目是否选中
	 *  */
	@Column(name = "selected")
	public Integer selected;

	@Column(name = "category")
	private String category;

	@Column(name = "username")
	private String username;

	public ChannelItem() {
	}

	public ChannelItem(Integer id, String name, Integer orderId, Integer selected, String category, String username) {
		this.id = id;
		this.name = name;
		this.orderId = orderId;
		this.selected = selected;
		this.category = category;
		this.username = username;
	}

	@Override
	public String toString() {
		return "ChannelItem{" +
				"id=" + id +
				", name='" + name + '\'' +
				", orderId=" + orderId +
				", selected=" + selected +
				", category='" + category + '\'' +
				", username='" + username + '\'' +
				'}';
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getSelected() {
		return selected;
	}

	public void setSelected(Integer selected) {
		this.selected = selected;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}