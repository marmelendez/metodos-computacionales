//Problema 1
(define (insert1 n lst flag)
    (cond 
        ((AND (null? lst) (= flag 1)) (cons n '()))
        ((null? lst) '())
        (else 
            (if (AND (< n (car lst)) (= flag 1))
                (cons n (cons (car lst) (insert1 n (cdr lst) -1)))
                (cons (car lst) (insert1 n (cdr lst) flag))
            )
        )
    )
)

(define (insert n lst)
    (if (null? lst) 
        (cons n '())
        (insert1 n lst 1)
    )
)

//Problema 6
(define (deep-reverse l)
    (if (null? l)
        '()
        (if (list? (car l))
            (append (deep-reverse (cdr l)) (list (deep-reverse (car l))))
            (append (deep-reverse (cdr l)) (list (car l)))
        )
    )
)