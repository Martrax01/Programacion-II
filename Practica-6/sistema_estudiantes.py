import tkinter as tk
from tkinter import ttk, messagebox
import json
import os

class Persona:
    def __init__(self, nombre, apellido, edad):
        self.nombre = nombre
        self.apellido = apellido
        self.edad = edad
    
    def obtener_nombre_completo(self):
        return f"{self.nombre} {self.apellido}"


class Estudiante(Persona):
    def __init__(self, codigo, nombre, apellido, edad, carrera, promedio):
        super().__init__(nombre, apellido, edad)
        self.codigo = codigo
        self.carrera = carrera
        self.promedio = promedio
        
#convertidor de json
    def to_dict(self):
        return {
            "codigo": self.codigo,
            "nombre": self.nombre,
            "apellido": self.apellido,
            "edad": self.edad,
            "carrera": self.carrera,
            "promedio": self.promedio
        }
    
    @staticmethod
    def from_dict(data):
        return Estudiante(
            data["codigo"],
            data["nombre"],
            data["apellido"],
            data["edad"],
            data["carrera"],
            data["promedio"]
        )


class GestorEstudiantes:
    def __init__(self, archivo="estudiantes.json"):
        self.archivo = archivo
        self.estudiantes = []
        self.cargar_datos()
    
    def agregar(self, estudiante):
        for est in self.estudiantes:
            if est.codigo == estudiante.codigo:
                return False
        self.estudiantes.append(estudiante)
        self.guardar_datos()
        return True
    
    def eliminar(self, codigo):
        for i, est in enumerate(self.estudiantes):
            if est.codigo == codigo:
                self.estudiantes.pop(i)
                self.guardar_datos()
                return True
        return False
    
    def modificar(self, codigo, estudiante_nuevo):
        for i, est in enumerate(self.estudiantes):
            if est.codigo == codigo:
                self.estudiantes[i] = estudiante_nuevo
                self.guardar_datos()
                return True
        return False
    
    def buscar(self, codigo):
        for est in self.estudiantes:
            if est.codigo == codigo:
                return est
        return None
    
    def obtener_todos(self):
        return self.estudiantes
    
    def guardar_datos(self):
        try:
            with open(self.archivo, 'w', encoding='utf-8') as f:
                datos = [est.to_dict() for est in self.estudiantes]
                json.dump(datos, f, indent=4, ensure_ascii=False)
        except Exception as e:
            print(f"Error al guardar: {e}")
    
    def cargar_datos(self):
        if os.path.exists(self.archivo):
            try:
                with open(self.archivo, 'r', encoding='utf-8') as f:
                    datos = json.load(f)
                    self.estudiantes = [Estudiante.from_dict(d) for d in datos]
            except Exception as e:
                print(f"Error al cargar: {e}")
                self.estudiantes = []


#INTERFAZ

class SistemaEstudiantesGUI:
    def __init__(self, root):
        self.root = root
        self.root.title("Sistema de Gestión de Estudiantes")
        self.root.geometry("900x600")
        self.root.resizable(False, False)
        
        self.gestor = GestorEstudiantes()
        
        self.crear_widgets()
        self.actualizar_tabla()
    
    def crear_widgets(self):
        frame_form = tk.LabelFrame(self.root, text="Datos del Estudiante", padx=10, pady=10)
        frame_form.pack(padx=10, pady=10, fill="x")
        

        tk.Label(frame_form, text="Código:").grid(row=0, column=0, sticky="e", padx=5, pady=5)
        self.entry_codigo = tk.Entry(frame_form, width=30)
        self.entry_codigo.grid(row=0, column=1, padx=5, pady=5)
        
        tk.Label(frame_form, text="Nombre:").grid(row=0, column=2, sticky="e", padx=5, pady=5)
        self.entry_nombre = tk.Entry(frame_form, width=30)
        self.entry_nombre.grid(row=0, column=3, padx=5, pady=5)
        
        tk.Label(frame_form, text="Apellido:").grid(row=1, column=0, sticky="e", padx=5, pady=5)
        self.entry_apellido = tk.Entry(frame_form, width=30)
        self.entry_apellido.grid(row=1, column=1, padx=5, pady=5)
        
        tk.Label(frame_form, text="Edad:").grid(row=1, column=2, sticky="e", padx=5, pady=5)
        self.entry_edad = tk.Entry(frame_form, width=30)
        self.entry_edad.grid(row=1, column=3, padx=5, pady=5)
        
        tk.Label(frame_form, text="Carrera:").grid(row=2, column=0, sticky="e", padx=5, pady=5)
        self.entry_carrera = tk.Entry(frame_form, width=30)
        self.entry_carrera.grid(row=2, column=1, padx=5, pady=5)
        
        tk.Label(frame_form, text="Promedio:").grid(row=2, column=2, sticky="e", padx=5, pady=5)
        self.entry_promedio = tk.Entry(frame_form, width=30)
        self.entry_promedio.grid(row=2, column=3, padx=5, pady=5)
        
        frame_buttons = tk.Frame(self.root)
        frame_buttons.pack(pady=10)
        
        tk.Button(frame_buttons, text="Agregar", command=self.agregar_estudiante, 
                 bg="#4CAF50", fg="white", width=12, height=2).grid(row=0, column=0, padx=5)
        
        tk.Button(frame_buttons, text="Modificar", command=self.modificar_estudiante,
                 bg="#2196F3", fg="white", width=12, height=2).grid(row=0, column=1, padx=5)
        
        tk.Button(frame_buttons, text="Eliminar", command=self.eliminar_estudiante,
                 bg="#f44336", fg="white", width=12, height=2).grid(row=0, column=2, padx=5)
        
        tk.Button(frame_buttons, text="Limpiar", command=self.limpiar_campos,
                 bg="#9E9E9E", fg="white", width=12, height=2).grid(row=0, column=3, padx=5)
        
        tk.Button(frame_buttons, text="Buscar", command=self.buscar_estudiante,
                 bg="#FF9800", fg="white", width=12, height=2).grid(row=0, column=4, padx=5)
        
        frame_tabla = tk.LabelFrame(self.root, text="Lista de Estudiantes", padx=10, pady=10)
        frame_tabla.pack(padx=10, pady=10, fill="both", expand=True)
        
        scrollbar = tk.Scrollbar(frame_tabla)
        scrollbar.pack(side="right", fill="y")

        self.tabla = ttk.Treeview(frame_tabla, yscrollcommand=scrollbar.set,
                                  columns=("codigo", "nombre", "apellido", "edad", "carrera", "promedio"),
                                  show="headings", height=10)
        
        self.tabla.heading("codigo", text="Código")
        self.tabla.heading("nombre", text="Nombre")
        self.tabla.heading("apellido", text="Apellido")
        self.tabla.heading("edad", text="Edad")
        self.tabla.heading("carrera", text="Carrera")
        self.tabla.heading("promedio", text="Promedio")
        
        self.tabla.column("codigo", width=80, anchor="center")
        self.tabla.column("nombre", width=150)
        self.tabla.column("apellido", width=150)
        self.tabla.column("edad", width=60, anchor="center")
        self.tabla.column("carrera", width=200)
        self.tabla.column("promedio", width=80, anchor="center")
        
        self.tabla.pack(fill="both", expand=True)
        scrollbar.config(command=self.tabla.yview)

        self.tabla.bind("<Double-1>", self.seleccionar_estudiante)
    
    def agregar_estudiante(self):
        try:
            codigo = self.entry_codigo.get().strip()
            nombre = self.entry_nombre.get().strip()
            apellido = self.entry_apellido.get().strip()
            edad = int(self.entry_edad.get().strip())
            carrera = self.entry_carrera.get().strip()
            promedio = float(self.entry_promedio.get().strip())
            
            if not codigo or not nombre or not apellido or not carrera:
                messagebox.showwarning("Advertencia", "Todos los campos son obligatorios")
                return
            
            estudiante = Estudiante(codigo, nombre, apellido, edad, carrera, promedio)
            
            if self.gestor.agregar(estudiante):
                messagebox.showinfo("Éxito", "Estudiante agregado correctamente")
                self.limpiar_campos()
                self.actualizar_tabla()
            else:
                messagebox.showerror("Error", "Ya existe un estudiante con ese código")
        
        except ValueError:
            messagebox.showerror("Error", "Edad y Promedio deben ser números")
    
    def modificar_estudiante(self):
        try:
            codigo = self.entry_codigo.get().strip()
            
            if not codigo:
                messagebox.showwarning("Advertencia", "Ingrese el código del estudiante a modificar")
                return
            
            nombre = self.entry_nombre.get().strip()
            apellido = self.entry_apellido.get().strip()
            edad = int(self.entry_edad.get().strip())
            carrera = self.entry_carrera.get().strip()
            promedio = float(self.entry_promedio.get().strip())
            
            if not nombre or not apellido or not carrera:
                messagebox.showwarning("Advertencia", "Todos los campos son obligatorios")
                return
            
            estudiante_nuevo = Estudiante(codigo, nombre, apellido, edad, carrera, promedio)
            
            if self.gestor.modificar(codigo, estudiante_nuevo):
                messagebox.showinfo("Éxito", "Estudiante modificado correctamente")
                self.limpiar_campos()
                self.actualizar_tabla()
            else:
                messagebox.showerror("Error", "No se encontró el estudiante")
        
        except ValueError:
            messagebox.showerror("Error", "Edad y Promedio deben ser números")
    
    def eliminar_estudiante(self):
        codigo = self.entry_codigo.get().strip()
        
        if not codigo:
            messagebox.showwarning("Advertencia", "Ingrese el código del estudiante a eliminar")
            return
        
        respuesta = messagebox.askyesno("Confirmar", "¿Está seguro de eliminar este estudiante?")
        
        if respuesta:
            if self.gestor.eliminar(codigo):
                messagebox.showinfo("Éxito", "Estudiante eliminado correctamente")
                self.limpiar_campos()
                self.actualizar_tabla()
            else:
                messagebox.showerror("Error", "No se encontró el estudiante")
    
    def buscar_estudiante(self):
        codigo = self.entry_codigo.get().strip()
        
        if not codigo:
            messagebox.showwarning("Advertencia", "Ingrese el código a buscar")
            return
        
        estudiante = self.gestor.buscar(codigo)
        
        if estudiante:
            self.entry_nombre.delete(0, tk.END)
            self.entry_nombre.insert(0, estudiante.nombre)
            
            self.entry_apellido.delete(0, tk.END)
            self.entry_apellido.insert(0, estudiante.apellido)
            
            self.entry_edad.delete(0, tk.END)
            self.entry_edad.insert(0, estudiante.edad)
            
            self.entry_carrera.delete(0, tk.END)
            self.entry_carrera.insert(0, estudiante.carrera)
            
            self.entry_promedio.delete(0, tk.END)
            self.entry_promedio.insert(0, estudiante.promedio)
        else:
            messagebox.showerror("Error", "No se encontró el estudiante")
    
    def seleccionar_estudiante(self, event):
        seleccion = self.tabla.selection()
        if seleccion:
            item = self.tabla.item(seleccion[0])
            valores = item['values']
            
            self.entry_codigo.delete(0, tk.END)
            self.entry_codigo.insert(0, valores[0])
            
            self.entry_nombre.delete(0, tk.END)
            self.entry_nombre.insert(0, valores[1])
            
            self.entry_apellido.delete(0, tk.END)
            self.entry_apellido.insert(0, valores[2])
            
            self.entry_edad.delete(0, tk.END)
            self.entry_edad.insert(0, valores[3])
            
            self.entry_carrera.delete(0, tk.END)
            self.entry_carrera.insert(0, valores[4])
            
            self.entry_promedio.delete(0, tk.END)
            self.entry_promedio.insert(0, valores[5])
    
    def limpiar_campos(self):
        self.entry_codigo.delete(0, tk.END)
        self.entry_nombre.delete(0, tk.END)
        self.entry_apellido.delete(0, tk.END)
        self.entry_edad.delete(0, tk.END)
        self.entry_carrera.delete(0, tk.END)
        self.entry_promedio.delete(0, tk.END)
        self.entry_codigo.focus()
    
    def actualizar_tabla(self):
        for item in self.tabla.get_children():
            self.tabla.delete(item)
        
        for est in self.gestor.obtener_todos():
            self.tabla.insert("", "end", values=(
                est.codigo,
                est.nombre,
                est.apellido,
                est.edad,
                est.carrera,
                est.promedio
            ))



if __name__ == "__main__":
    root = tk.Tk()
    app = SistemaEstudiantesGUI(root)
    root.mainloop()
