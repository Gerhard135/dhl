package main;


public class List {

    private Node first, last, current;

    private class Node {
        private Node next, previous;
        private Object content;

        public Node(Object content) {
            this.content = content;
            next = null;
            previous = null;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getNext() {
            return next;
        }

        public void setPrevious(Node previous) {
            this.previous = previous;
        }

        public Node getPrevious() {
            return previous;
        }

        public void setContent(Object content) {
            this.content = content;
        }

        public Object getContent() {
            return content;
        }
    }

    public List() {
        first = null;
        last = null;
        current = null;
    }


    public boolean isEmpty() {
        return first == null;
    }


    public boolean hasAccess() {
        return current != null;
    }


    public void next() {
        if (!isEmpty() && hasAccess() && current != last) {
            current = current.getNext();
        } else {
            current = null;
        }
    }

    public void toFirst() {
        if (!isEmpty()) {
            current = first;
        }
    }


    public void toLast() {
        if (!isEmpty()) {
            current = last;
        }
    }


    public Object getObject() {
        if (hasAccess()) {
            return current.getContent();
        } else {
            return null;
        }
    }

    public void setObject(Object o) {
        if (hasAccess() && o != null) {
            current.setContent(o);
        }
    }


    public void append(Object o) {
        if (o != null) {
            Node n = new Node(o);
            if (isEmpty()) {
                first = n;
                last = n;
            } else {
                last.setNext(n);
                n.setPrevious(last);
                last = n;
            }
        }
    }


    public void insert(Object o) {
       
        if (o != null) {
            if (isEmpty()) {
                append(o);
            } else {
                if (hasAccess()) {
                    
                    if (current == first) {
                        Node n = new Node(o);
                        first = n;
                        n.setNext(current);
                        current.setPrevious(first);
                    }
                    else {

                        Node n = new Node(o);
                        current.getPrevious().setNext(n);
                        n.setPrevious(current.getPrevious());
                        current.setPrevious(n);
                        n.setNext(current);
                    }
                }
            }
        }

    }
    
  
    public void concat(List list) {
        if (list != null){
            if (!list.isEmpty()) {
                list.toFirst();
                while (list.hasAccess()) {
                    append(list.getObject());
                    
                    list.next();
                }
                list = new List();
            }
        }
    }
    



    public void remove() {
      
        if (hasAccess()) {
            
            if (first == last) {
                first = null;
                last = null;
                current = null;
            }
            else {
                if (current == first) {
                    first = current.next;
                    first.setPrevious(null);
                    current = first;
                }
                else {
                    if (current == last) {
                        last = current.previous;
                        last.setNext(null);
                        current = null;
                    }
                        (current.getPrevious()).setNext(current.getNext());
                        (current.getNext()).setPrevious(current.getPrevious());
                        current = current.getNext();
                    }

                }
            }
        }
    }   