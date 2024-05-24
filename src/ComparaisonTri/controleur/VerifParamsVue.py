from PyQt5.QtWidgets import QMessageBox

class VerifParamsVue:
    @staticmethod
    def champs_nonvides(taille, pourcentage):
        """
        Vérifie que tous les champs sont remplis
        et affiche un message d'erreur si ce n'est pas le cas.
        """
        if not (taille and pourcentage):
            QMessageBox.warning(None, "Champs vides", "Veuillez remplir tous les champs avant de continuer.")
            return False
        return True

    @staticmethod
    def entiers(taille, pourcentage):
        """
        Vérifie que taille et pourcentage sont des entiers
        et affiche un message d'erreur si ce n'est pas le cas.
        """
        if not taille.isdigit() or not pourcentage.isdigit():
            QMessageBox.warning(None, "Valeurs invalides", "La taille du tableau et le pourcentage de désordre doivent être des entiers.")
            return False
        return True

    @staticmethod
    def pourcentage_valide(pourcentage):
        """
        Vérifie que pourcentage est un pourcentage valide (compris entre 0 et 100)
        et affiche un message d'erreur si ce n'est pas le cas.
        """
        pourcentage_desordre = int(pourcentage)
        if not 0 <= pourcentage_desordre <= 100:
            QMessageBox.warning(None, "Valeur invalide", "Le pourcentage de désordre doit être compris entre 0 et 100 inclus.")
            return False
        return True

    @staticmethod
    def est_valide(taille, pourcentage):
        """Vérifie si tous les paramètres sont valides."""
        return (VerifParamsVue.champs_nonvides(taille, pourcentage) and
                VerifParamsVue.entiers(taille, pourcentage) and
                VerifParamsVue.pourcentage_valide(pourcentage))