package hu.farcsal.cms.entity;

import hu.farcsal.cms.entity.key.PrimaryLongObject;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

/**
 *
 * @author zoli
 */
@Entity
@Table(name="page-layout-columns")
public class PageLayoutColumn extends PrimaryLongObject<PageLayoutColumn> {
    
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ElementCollection
    @Column(name = "widget", nullable = false)
    @OrderColumn(name="index", nullable = false)
    @CollectionTable(
        name="page-layout-column-widgets",
        joinColumns=@JoinColumn(name="column-id", nullable = false)
    )
    private List<String> widgets = new ArrayList<>();

    @Column(name = "content-index", nullable = false)
    private int contentIndex = 0;

    protected PageLayoutColumn() {
    }
    
    public PageLayoutColumn(List<String> widgets) {
        this(widgets, 0);
    }
    
    public PageLayoutColumn(List<String> widgets, int contentIndex) {
        if (widgets != null) this.widgets.addAll(widgets);
        this.contentIndex = contentIndex;
    }
    
    @Override
    public Long getId() {
        return id;
    }

    public List<String> getWidgets() {
        return widgets;
    }

    public int getContentIndex() {
        return contentIndex;
    }

    public void setContentIndex(int contentIndex) {
        this.contentIndex = contentIndex;
    }

}
