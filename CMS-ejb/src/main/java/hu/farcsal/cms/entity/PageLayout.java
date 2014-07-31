package hu.farcsal.cms.entity;

import hu.farcsal.cms.entity.key.PrimaryLongObject;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author zoli
 */
@Entity
@Table(name="page-layouts")
public class PageLayout extends PrimaryLongObject<PageLayout> {
    
    public static enum Type {
        NO_COLUMNS,
        TWO_COLUMNS,
        LEFT_COLUMN,
        RIGHT_COLUMN
    }
    
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type = Type.NO_COLUMNS;
    
    @ManyToOne
    @JoinColumn(name="left-column", nullable = true)
    private PageLayoutColumn leftColumn;
    
    @ManyToOne
    @JoinColumn(name="right-column", nullable = true)
    private PageLayoutColumn rightColumn;
    
    @OneToMany(mappedBy = "layout")
    private List<Page> pages;
    
    public PageLayout() {
        super();
    }
    
    public PageLayout(Type type, PageLayoutColumn leftColumn, PageLayoutColumn rightColumn) {
        this.type = type;
        this.leftColumn = leftColumn;
        this.rightColumn = rightColumn;
    }
    
    @Override
    public Long getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public PageLayoutColumn getLeftColumn() {
        return leftColumn;
    }

    public void setLeftColumn(PageLayoutColumn leftColumn) {
        this.leftColumn = leftColumn;
    }

    public PageLayoutColumn getRightColumn() {
        return rightColumn;
    }

    public void setRightColumn(PageLayoutColumn rightColumn) {
        this.rightColumn = rightColumn;
    }

    public List<Page> getPages() {
        return pages;
    }
    
}
