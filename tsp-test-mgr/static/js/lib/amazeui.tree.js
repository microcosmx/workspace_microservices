(function(f){if(typeof exports==="object"&&typeof module!=="undefined"){module.exports=f()}else if(typeof define==="function"&&define.amd){define([],f)}else{var g;if(typeof window!=="undefined"){g=window}else if(typeof global!=="undefined"){g=global}else if(typeof self!=="undefined"){g=self}else{g=this}g.AMUITree = f()}})(function(){var define,module,exports;return (function e(t,n,r){function s(o,u){if(!n[o]){if(!t[o]){var a=typeof require=="function"&&require;if(!u&&a)return a(o,!0);if(i)return i(o,!0);var f=new Error("Cannot find module '"+o+"'");throw f.code="MODULE_NOT_FOUND",f}var l=n[o]={exports:{}};t[o][0].call(l.exports,function(e){var n=t[o][1][e];return s(n?n:e)},l,l.exports,e,t,n,r)}return n[o].exports}var i=typeof require=="function"&&require;for(var o=0;o<r.length;o++)s(r[o]);return s})({1:[function(_dereq_,module,exports){
(function (global){
/*
 * Amaze UI Tree
 *
 * via: https://github.com/ExactTarget/fuelux
 *
 * Copyright (c) 2014 ExactTarget
 * Licensed under the BSD New license.
 */

'use strict';

var $ = (typeof window !== "undefined" ? window['jQuery'] : typeof global !== "undefined" ? global['jQuery'] : null);

var old = $.fn.tree;

// TREE CONSTRUCTOR AND PROTOTYPE

var Tree = function Tree(element, options) {
  this.$element = $(element);
  this.options = $.extend({}, $.fn.tree.defaults, options);

  // icons
  var itemIcon = this.$element.data('itemIcon') || this.options.itemIcon;
  var itemSelectedIcon = this.$element.data('itemSelectedIcon') ||
    this.options.itemSelectedIcon;
  this.folderIcon = this._getIconClass(this.options.folderIcon);
  this.folderOpenIcon = this._getIconClass(this.options.folderOpenIcon);
  this.itemIcon = this._getIconClass(itemIcon);
  this.itemSelectedIcon = this._getIconClass(itemSelectedIcon);

  // classes
  this.classes = {};
  $.each(this.options.classes, $.proxy(function(key, value) {
    this.classes[key] = this._getClass(value);
  }, this));

  if (this.options.itemSelect) {
    this.$element.on('click.tree.amui', '.' + this.classes.item, $.proxy(function(e) {
      this.selectItem(e.currentTarget);
    }, this));
  }

  this.$element.on('click.tree.amui', '.' + this.classes.branchName, $.proxy(function(e) {
    this.toggleFolder(e.currentTarget);
  }, this));

  // folderSelect default is true
  if (this.options.folderSelect) {
    this.$element.addClass(this.options.classPrefix + '-folder-select');
    this.$element.off('click.tree.amui', '.' + this.classes.branchName);
    this.$element.on('click.tree.amui', '.' + this.classes.iconCaret, $.proxy(function(e) {
      this.toggleFolder($(e.currentTarget).parent());
    }, this));
    this.$element.on('click.tree.amui', '.' + this.classes.branchName, $.proxy(function(e) {
      this.selectFolder($(e.currentTarget));
    }, this));
  }

  this.render();
};

Tree.prototype = {
  constructor: Tree,

  deselectAll: function deselectAll(nodes) {
    var _this = this;
    // clear all child tree nodes and style as deselected
    nodes = nodes || this.$element;
    var $selectedElements = $(nodes).find('.' + this.classes.selected);
    $selectedElements.each(function(index, element) {
      styleNodeDeselected(_this, $(element),
        $(element).find('.' + _this.classes.icon));
    });
    return $selectedElements;
  },

  destroy: function destroy() {
    // any external bindings [none]
    // empty elements to return to original markup
    this.$element.find('li:not([data-template])').remove();

    this.$element.remove();
    // returns string of markup
    return this.$element[0].outerHTML;
  },

  render: function render() {
    this.populate(this.$element);
  },

  populate: function populate($el) {
    var _this = this;
    var $parent = ($el.hasClass(this.options.classPrefix)) ? $el : $el.parent();
    var $loader = $parent.find('.' + this.classes.loader).eq(0);
    var treeData = $parent.data();

    $loader.removeClass(this.options.hiddenClass);

    // 根据模板和数据生成 tree
    this.options.dataSource(treeData, function(items) {
      $loader.addClass(_this.options.hiddenClass);

      $.each(items.data, function(index, value) {
        var $entity;

        // 判断是否包含子级
        if (value.type === 'folder') {
          $entity = _this.$element.find('[data-template=treebranch]').first().
            clone().
            removeClass(_this.options.hiddenClass).removeData('template');
          $entity.data(value);
          // add folder icon class
          $entity.find('.' + _this.classes.iconFolder).addClass(_this.folderIcon);
          $entity.find('.' + _this.classes.branchName + ' > .' + _this.classes.label).html(value.title);
        } else if (value.type === 'item') {
          $entity = _this.$element.find('[data-template=treeitem]').first()
            .clone().removeClass(_this.options.hiddenClass).removeData('template');
          $entity.find('.' + _this.classes.itemName + ' > .' + _this.classes.label).html(value.title);
          $entity.data(value);
          $entity.find('.' + _this.classes.iconItem).addClass(_this.itemIcon);
        }

        // Decorate $entity with data or other attributes making the
        // element easily accessable with libraries like jQuery.
        //
        // Values are contained within the object returned
        // for folders and items as attr:
        //
        // {
        //     title: "An Item",
        //     type: 'item',
        //     attr: {
        //         'classNames': 'required-item red-text',
        //         'icon': '',
        //         'id': guid
        //     }
        // };
        //

        // add attributes to tree-branch or tree-item
        var attr = value.attr || [];
        $.each(attr, function(key, value) {
          switch (key) {
            case 'classNames':
              $entity.addClass(value);
              break;

            // allow custom icons
            case 'icon':
              var classes = [_this.classes.icon, _this.classes.iconItem, value].join(' ');
              $entity.find('.' + _this.classes.iconItem).removeClass().addClass(classes);
              $entity.data(key, value);
              break;

            // ARIA support
            case 'id':
              $entity.attr(key, value);
              $entity.attr('aria-labelledby', value + '-label');
              $entity.find('.' + _this.classes.branchName + ' > .' + _this.classes.label).attr('id', value + '-label');
              break;

            // style, data-*
            default:
              $entity.attr(key, value);
              break;
          }
        });

        // add child nodes
        if ($el.hasClass(_this.classes.branchHeader)) {
          $parent.find('.' + _this.classes.branchChildren).eq(0).append($entity);
        } else {
          $el.append($entity);
        }
      });

      // return newly populated folder
      _this.$element.trigger('loaded.tree.amui', $parent);
    });
  },

  selectTreeNode: function selectItem(clickedElement, nodeType) {
    var clicked = {};	// object for clicked element
    clicked.$element = $(clickedElement);

    var selected = {}; // object for selected elements
    selected.$elements = this.$element.find('.' + this.classes.selected);
    selected.dataForEvent = [];

    // determine clicked element and it's icon
    if (nodeType === 'folder') {
      // make the clicked.$element the container branch
      clicked.$element = clicked.$element.closest('.' + this.classes.branch);
      clicked.$icon = clicked.$element.find('.' + this.classes.iconFolder);
    } else {
      clicked.$icon = clicked.$element.find('.' + this.classes.iconItem);
    }
    clicked.elementData = clicked.$element.data();

    // the below functions pass objects by copy/reference and use modified object in this function
    if (this.options.multiSelect) {
      multiSelectSyncNodes(this, clicked, selected);
    } else {
      singleSelectSyncNodes(this, clicked, selected);
    }

    // all done with the DOM, now fire events
    this.$element.trigger(selected.eventType + '.tree.amui', {
      target: clicked.elementData,
      selected: selected.dataForEvent
    });

    clicked.$element.trigger('updated.tree.amui', {
      selected: selected.dataForEvent,
      item: clicked.$element,
      eventType: selected.eventType
    });
  },

  discloseFolder: function discloseFolder(el) {
    var $el = $(el);
    var $branch = $el.closest('.' + this.classes.branch);
    var $treeFolderContent = $branch.find('.' + this.classes.branchChildren);
    var $treeFolderContentFirstChild = $treeFolderContent.eq(0);

    // take care of the styles
    $branch.addClass(this.classes.open);
    $branch.attr('aria-expanded', 'true');
    $treeFolderContentFirstChild.removeClass(this.options.hiddenClass);
    $branch.find('> .' + this.classes.branchHeader + ' .' + this.classes.iconFolder).eq(0)
      .removeClass(this.folderIcon)
      .addClass(this.folderOpenIcon);

    // add the children to the folder
    if (!$treeFolderContent.children().length) {
      this.populate($treeFolderContent);
    }

    this.$element.trigger('disclosedFolder.tree.amui', $branch.data());
  },

  closeFolder: function closeFolder(el) {
    var $el = $(el);
    var $branch = $el.closest('.' + this.classes.branch);
    var $treeFolderContent = $branch.find('.' + this.classes.branchChildren);
    var $treeFolderContentFirstChild = $treeFolderContent.eq(0);

    //take care of the styles
    $branch.removeClass(this.classes.open);
    $branch.attr('aria-expanded', 'false');
    $treeFolderContentFirstChild.addClass(this.options.hiddenClass);
    $branch.find('> .' + this.classes.branchHeader + ' .' + this.classes.iconFolder).eq(0)
      .removeClass(this.folderOpenIcon)
      .addClass(this.folderIcon);

    // remove chidren if no cache
    if (!this.options.cacheItems) {
      $treeFolderContentFirstChild.empty();
    }

    this.$element.trigger('closed.tree.amui', $branch.data());
  },

  toggleFolder: function toggleFolder(el) {
    var $el = $(el);

    if ($el.find('.' + this.folderIcon).length) {
      this.discloseFolder(el);
    } else if ($el.find('.' + this.folderOpenIcon).length) {
      this.closeFolder(el);
    }
  },

  selectFolder: function selectFolder(el) {
    if (this.options.folderSelect) {
      this.selectTreeNode(el, 'folder');
    }
  },

  selectItem: function selectItem(el) {
    if (this.options.itemSelect) {
      this.selectTreeNode(el, 'item');
    }
  },

  selectedItems: function selectedItems() {
    var $sel = this.$element.find('.' + this.classes.selected);
    var data = [];

    $.each($sel, function(index, value) {
      data.push($(value).data());
    });
    return data;
  },

  // collapses open folders
  closeAll: function collapse() {
    var self = this;
    var reportedClosed = [];

    var closedReported = function closedReported(event, closed) {
      reportedClosed.push(closed);

      if (self.$element.find('.' + self.classes.branch + '.' + self.classes.open + ':not(".am-hide")').length === 0) {
        self.$element.trigger('closedAll.tree.amui', {
          tree: self.$element,
          reportedClosed: reportedClosed
        });
        self.$element.off('loaded.tree.amui', self.$element, closedReported);
      }
    };

    //trigger callback when all folders have reported closed
    self.$element.on('closed.tree.amui', closedReported);

    self.$element.find('.' + self.classes.branch + '.' + self.classes.open + ':not(".am-hide")').each(function() {
      self.closeFolder(this);
    });
  },

  //disclose visible will only disclose visible tree folders
  discloseVisible: function discloseVisible() {
    var self = this;

    var $openableFolders = self.$element.find('.' + this.classes.branch + ':not(".' + this.classes.open + ', .am-hide")');
    var reportedOpened = [];

    var openReported = function openReported(event, opened) {
      reportedOpened.push(opened);

      if (reportedOpened.length === $openableFolders.length) {
        self.$element.trigger('disclosedVisible.tree.amui', {
          tree: self.$element,
          reportedOpened: reportedOpened
        });
        /*
         * Unbind the `openReported` event. `discloseAll` may be running and we want to reset this
         * method for the next iteration.
         */
        self.$element.off('loaded.tree.amui', self.$element, openReported);
      }
    };

    //trigger callback when all folders have reported opened
    self.$element.on('loaded.tree.amui', openReported);

    // open all visible folders
    self.$element.find('.' + this.classes.branch + ':not(".' + this.classes.open + ', .am-hide")').each(function triggerOpen() {
      self.discloseFolder($(this).find('.' + self.classes.branchHeader));
    });
  },

  /**
   * Disclose all will keep listening for `loaded.tree.amui` and if `$(tree-el).data('ignore-disclosures-limit')`
   * is `true` (defaults to `true`) it will attempt to disclose any new closed folders than were
   * loaded in during the last disclosure.
   */
  discloseAll: function discloseAll() {
    var self = this;

    //first time
    if (typeof self.$element.data('disclosures') === 'undefined') {
      self.$element.data('disclosures', 0);
    }

    var isExceededLimit = (self.options.disclosuresUpperLimit >= 1 && self.$element.data('disclosures') >= self.options.disclosuresUpperLimit);
    var isAllDisclosed = self.$element.find('.' + this.classes.branch + ':not(".' + this.classes.open + ', .am-hide")').length === 0;


    if (!isAllDisclosed) {
      if (isExceededLimit) {
        self.$element.trigger('exceededDisclosuresLimit.tree.amui', {
          tree: self.$element,
          disclosures: self.$element.data('disclosures')
        });

        /*
         * If you've exceeded the limit, the loop will be killed unless you
         * explicitly ignore the limit and start the loop again:
         *
         *    $tree.one('exceededDisclosuresLimit.tree.amui', function () {
         *        $tree.data('ignore-disclosures-limit', true);
         *        $tree.tree('discloseAll');
         *    });
         */
        if (!self.$element.data('ignore-disclosures-limit')) {
          return;
        }
      }

      self.$element.data('disclosures', self.$element.data('disclosures') + 1);

      /*
       * A new branch that is closed might be loaded in, make sure those get handled too.
       * This attachment needs to occur before calling `discloseVisible` to make sure that
       * if the execution of `discloseVisible` happens _super fast_ (as it does in our QUnit tests
       * this will still be called. However, make sure this only gets called _once_, because
       * otherwise, every single time we go through this loop, _another_ event will be bound
       * and then when the trigger happens, this will fire N times, where N equals the number
       * of recursive `discloseAll` executions (instead of just one)
       */
      self.$element.one('disclosedVisible.tree.amui', function() {
        self.discloseAll();
      });

      /*
       * If the page is very fast, calling this first will cause `disclosedVisible.tree.amui` to not
       * be bound in time to be called, so, we need to call this last so that the things bound
       * and triggered above can have time to take place before the next execution of the
       * `discloseAll` method.
       */
      self.discloseVisible();
    } else {
      self.$element.trigger('disclosedAll.tree.amui', {
        tree: self.$element,
        disclosures: self.$element.data('disclosures')
      });

      //if `cacheItems` is false, and they call closeAll, the data is trashed and therefore
      //disclosures needs to accurately reflect that
      if (!self.options.cacheItems) {
        self.$element.one('closeAll.tree.amui', function() {
          self.$element.data('disclosures', 0);
        });
      }
    }
  },

  _getIconClass: function(icon, selector) {
    return (selector ? '.' : '') + 'am-icon-' + icon;
  },

  _getClass: function(subClass, selector) {
    return (selector ? '.' : '') + this.options.classPrefix + '-' + subClass;
  }
};


// ALIASES

Tree.prototype.openFolder = Tree.prototype.discloseFolder;


// PRIVATE FUNCTIONS

function styleNodeSelected(self, $element, $icon) {
  $element.addClass(self.classes.selected);
  if ($element.data('type') === 'item' && $icon.hasClass(self.itemIcon)) {
    $icon.removeClass(self.itemIcon).addClass(self.itemSelectedIcon); // make checkmark
  }
}

function styleNodeDeselected(self, $element, $icon) {
  $element.removeClass(self.classes.selected);
  if ($element.data('type') === 'item' && $icon.hasClass(self.itemSelectedIcon)) {
    $icon.removeClass(self.itemSelectedIcon).addClass(self.itemIcon); // make bullet
  }
}

function multiSelectSyncNodes(self, clicked, selected) {
  // search for currently selected and add to selected data list if needed
  $.each(selected.$elements, function(index, element) {
    var $element = $(element);
    if ($element[0] !== clicked.$element[0]) {
      selected.dataForEvent.push($($element).data());
    }
  });

  if (clicked.$element.hasClass(self.classes.selected)) {
    styleNodeDeselected(self, clicked.$element, clicked.$icon);
    // set event data
    selected.eventType = 'deselected';
  } else {
    styleNodeSelected(self, clicked.$element, clicked.$icon);
    // set event data
    selected.eventType = 'selected';
    selected.dataForEvent.push(clicked.elementData);
  }
}

function singleSelectSyncNodes(self, clicked, selected) {
  // element is not currently selected
  if (selected.$elements[0] !== clicked.$element[0]) {
    var clearedElements = self.deselectAll(self.$element);
    styleNodeSelected(self, clicked.$element, clicked.$icon);
    // set event data
    selected.eventType = 'selected';
    selected.dataForEvent = [clicked.elementData];
  } else {
    styleNodeDeselected(self, clicked.$element, clicked.$icon);
    // set event data
    selected.eventType = 'deselected';
    selected.dataForEvent = [];
  }
}


// TREE PLUGIN DEFINITION

$.fn.tree = function tree(option) {
  var args = Array.prototype.slice.call(arguments, 1);
  var methodReturn;

  var $set = this.each(function() {
    var $this = $(this);
    var data = $this.data('amui.tree');
    var options = typeof option === 'object' && option;

    if (!data) {
      $this.data('amui.tree', (data = new Tree(this, options)));
    }

    if (typeof option === 'string') {
      methodReturn = typeof data[option] === 'function' ?
        data[option].apply(data, args) : data[option];
    }
  });

  return (methodReturn === undefined) ? $set : methodReturn;
};

$.fn.tree.defaults = {
  dataSource: function dataSource(options, callback) {
  },
  multiSelect: false,
  cacheItems: true,
  folderSelect: true,
  itemSelect: true,
  /*
   * How many times `discloseAll` should be called before a stopping and firing
   * an `exceededDisclosuresLimit` event. You can force it to continue by
   * listening for this event, setting `ignore-disclosures-limit` to `true` and
   * starting `discloseAll` back up again. This lets you make more decisions
   * about if/when/how/why/how many times `discloseAll` will be started back
   * up after it exceeds the limit.
   *
   *    $tree.one('exceededDisclosuresLimit.tree.amui', function () {
   *        $tree.data('ignore-disclosures-limit', true);
   *        $tree.tree('discloseAll');
   *    });
   *
   * `disclusuresUpperLimit` defaults to `0`, so by default this trigger
   * will never fire. The true hard the upper limit is the browser's
   * ability to load new items (i.e. it will keep loading until the browser
   * falls over and dies). On the Fuel UX `index.html` page, the point at
   * which the page became super slow (enough to seem almost unresponsive)
   * was `4`, meaning 256 folders had been opened, and 1024 were attempting to open.
   */
  disclosuresUpperLimit: 0,
  folderIcon: 'plus-square-o',
  folderOpenIcon: 'minus-square-o',
  itemIcon: 'angle-right',
  itemSelectedIcon: 'check',

  hiddenClass: 'am-hide',

  classPrefix: 'am-tree',

  classes: {
    branch: 'branch',
    branchName: 'branch-name', // branch name
    branchChildren: 'branch-children',
    branchHeader: 'branch-header',
    item: 'item', // tree item
    itemName: 'item-name', // tree item name
    label: 'label',
    icon: 'icon', // tree icon
    iconFolder: 'icon-folder', // tree icon
    iconItem: 'icon-item', // tree icon,
    iconCaret: 'icon-caret',
    loader: 'loader', // tree loader
    selected: 'selected',
    open: 'open'
  }
};

$.fn.tree.Constructor = Tree;

$.fn.tree.noConflict = function() {
  $.fn.tree = old;
  return this;
};

module.exports = Tree;

}).call(this,typeof global !== "undefined" ? global : typeof self !== "undefined" ? self : typeof window !== "undefined" ? window : {})
},{}]},{},[1])(1)
});