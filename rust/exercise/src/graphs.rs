use std::collections::HashMap;
use std::rc::Rc;

type N<T> = Rc<Node<T>>;
type E<N> = Edge<N>;

#[derive(Debug, Eq, Hash, PartialEq, Clone)]
struct Node<T>
where
    T: Eq,
{
    id: T,
}

#[derive(Debug)]
pub struct Edge<N> {
    pub to: N,
}

#[derive(Debug)]
struct Graph<T>
where
    T: Eq,
{
    vertices: Vec<N<T>>,
    adj: HashMap<N<T>, Vec<E<N<T>>>>,
}

impl Graph<i32> {
    fn new() -> Graph<i32> {
        Graph {
            vertices: Vec::new(),
            adj: HashMap::new(),
        }
    }

    fn add_vertex(&mut self, value: i32) {
        let n = Node::new(value);
        self.vertices.push(n.clone());
        self.adj.insert(n, Vec::new());
    }

    fn add_edge(&mut self, from: i32, to: i32) {
        let from_node = Node::new(from);
        let to_node = Node::new(to);
        self.adj.get_mut(&from_node).unwrap().push(Edge { to: to_node });
    }

    fn is_connected(&self, from: i32, to: i32) -> bool {
        let from_node = Node::new(from);
        let to_node = Node::new(to);
        self.adj.get(&from_node).unwrap().iter().any(|e| e.to == to_node)
    }
}

impl Node<i32> {
    fn new(value: i32) -> N<i32> {
        Rc::new(Node {
            id: value,
        })
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_create() {
        let mut vertices: Vec<N<i32>> = vec![0, 1, 2, 3].iter().map(|i| Node::new(*i)).collect();
        let mut adj: HashMap<N<i32>, Vec<Edge<N<i32>>>> = HashMap::new();
        vertices.iter().for_each(|v| {
            adj.insert(v.clone(), Vec::new());
        });
        let mut g = Graph { vertices, adj };
        g.add_edge(0, 1);
        g.add_edge(0, 2);
        g.add_edge(1, 3);
        println!("{:?}", g);
        assert_eq!(g.is_connected(0, 1), true);
        assert_eq!(g.is_connected(0, 3), false);
    }
}
